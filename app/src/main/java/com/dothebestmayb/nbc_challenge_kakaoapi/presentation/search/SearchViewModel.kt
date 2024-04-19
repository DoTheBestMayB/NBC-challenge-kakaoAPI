package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.onError
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.onException
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.onSuccess
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.CheckMediaIsBookmarkedUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.ImageDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.VideoDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.debounce
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toWithBookmarked
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getKakaoImageUseCase: GetKakaoImageUseCase,
    private val checkMediaIsBookmarkedUseCase: CheckMediaIsBookmarkedUseCase,
    private val deleteBookmarkedImageUseCase: DeleteBookmarkedImageUseCase,
    private val insertBookmarkedImageUseCase: InsertBookmarkedImageUseCase,
) : ViewModel() {

    private val _images = MutableLiveData<List<ImageDocumentStatus>>()
    val images: LiveData<List<ImageDocumentStatus>>
        get() = _images

//    private val _videos = MutableLiveData<MediaInfo.VideoInfo>()
//    val videos: LiveData<MediaInfo.VideoInfo>
//        get() = _videos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _query = MutableLiveData<String>()
    val query: LiveData<String>
        get() = _query.debounce(DEBOUNCE_TIME)

    private var page = 1


    fun fetchDataFromServer() {
        val query = _query.value ?: return

        viewModelScope.launch {
            val imageResponse = getKakaoImageUseCase(query, page)
            imageResponse.onSuccess { imageSearchEntity ->
                val result = imageSearchEntity.documents.map {
                    it.toWithBookmarked(checkMediaIsBookmarkedUseCase(it.docUrl))
                }
                _images.value = result
            }.onError { code, message ->
                _error.value = "$code $message"
            }.onException {
                _error.value = "${it.message}"
            }
        }
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

    fun updateBookmarkState(mediaInfo: MediaInfo, bookmarked: Boolean) {
        val result = when (mediaInfo) {
            is ImageDocumentStatus -> mediaInfo.toEntity()
            is VideoDocumentStatus -> TODO()
        }
        // 북마킹 여부 업데이트
        _images.value = _images.value?.map {
            if (it == mediaInfo) {
                it.copy(isBookmarked = bookmarked)
            } else {
                it
            }
        }

        viewModelScope.launch {
            if (bookmarked) {
                if (!checkMediaIsBookmarkedUseCase(result.docUrl)) {
                    insertBookmarkedImageUseCase(listOf(result))
                }
            } else {
                deleteBookmarkedImageUseCase(result)
            }
        }
    }

    companion object {
        private const val DEBOUNCE_TIME = 300L
    }
}