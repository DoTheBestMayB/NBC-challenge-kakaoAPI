package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.onError
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.onException
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.onSuccess
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.CheckImageIsBookmarkedUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.CheckVideoIsBookmarkedUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.HeaderStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.HeaderType
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.ImageDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.VideoDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.debounce
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toWithBookmarked
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getKakaoImageUseCase: GetKakaoImageUseCase,
    private val getKakaoVideoUseCase: GetKakaoVideoUseCase,
    private val checkImageIsBookmarkedUseCase: CheckImageIsBookmarkedUseCase,
    private val deleteBookmarkedImageUseCase: DeleteBookmarkedImageUseCase,
    private val insertBookmarkedImageUseCase: InsertBookmarkedImageUseCase,
    private val checkVideoIsBookmarkedUseCase: CheckVideoIsBookmarkedUseCase,
    private val deleteBookmarkedVideoUseCase: DeleteBookmarkedVideoUseCase,
    private val insertBookmarkedVideoUseCase: InsertBookmarkedVideoUseCase,
) : ViewModel() {

    private val images = MutableLiveData<List<ImageDocumentStatus>>()
    private val videos = MutableLiveData<List<VideoDocumentStatus>>()

    private val _results = MediatorLiveData<List<MediaInfo>>().apply {
        addSource(images) {
            val imageValues: List<MediaInfo> = it?.let { documentStatuses ->
                listOf(HeaderStatus(HeaderType.IMAGE)) as List<MediaInfo> + documentStatuses as List<MediaInfo>
            } ?: emptyList()

            val videoValues: List<MediaInfo> = videos.value?.let { documentStatuses ->
                listOf(HeaderStatus(HeaderType.VIDEO)) as List<MediaInfo> + documentStatuses as List<MediaInfo>
            } ?: emptyList()

            value = imageValues + videoValues
        }

        addSource(videos) {
            val imageValues: List<MediaInfo> = images.value?.let { documentStatuses ->
                listOf(HeaderStatus(HeaderType.IMAGE)) as List<MediaInfo> + documentStatuses as List<MediaInfo>
            } ?: emptyList()

            val videoValues: List<MediaInfo> = it?.let { documentStatuses ->
                listOf(HeaderStatus(HeaderType.VIDEO)) as List<MediaInfo> + documentStatuses as List<MediaInfo>
            } ?: emptyList()

            value = imageValues + videoValues
        }
    }
    val results: LiveData<List<MediaInfo>>
        get() = _results

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _query = MutableLiveData<String>()
    val query: LiveData<String>
        get() = _query.debounce(DEBOUNCE_TIME)

    private var page = 1

    private val _event = MutableSharedFlow<SearchEvent>()
    val event = _event.asSharedFlow()

    fun fetchDataFromServer() {
        val query = _query.value

        if (query.isNullOrBlank()) {
            images.value = emptyList()
            videos.value = emptyList()
            return
        }

        viewModelScope.launch {
            val videoResponse = async { // 실제로 순차적이 아니라 동시에 하는지 확인하기(이미지도 받아오는지)
                getKakaoVideoUseCase(query, page)
            }

            val imageResponse = getKakaoImageUseCase(query, page)

            imageResponse.onSuccess { imageSearchEntity ->
                val result = imageSearchEntity.documents.map {
                    it.toWithBookmarked(checkImageIsBookmarkedUseCase(it.imageUrl))
                }
                images.value = result
            }.onError { code, message ->
                _error.value = "$code $message"
            }.onException {
                _error.value = "${it.message}"
            }

            videoResponse.await().onSuccess { videoSearchEntity ->
                val result = videoSearchEntity.documents.map {
                    it.toWithBookmarked(checkVideoIsBookmarkedUseCase(it.url))
                }
                videos.value = result
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

    fun updateBookmarkState(mediaInfo: MediaInfo, bookmarked: Boolean, isFromBus: Boolean) {
        when (mediaInfo) {
            is ImageDocumentStatus -> update(mediaInfo, bookmarked, isFromBus)
            is VideoDocumentStatus -> update(mediaInfo, bookmarked, isFromBus)
            is HeaderStatus -> Unit
        }
    }

    private fun update(
        imageDocumentStatus: ImageDocumentStatus,
        bookmarked: Boolean,
        isFromBus: Boolean
    ) {
        val entity = imageDocumentStatus.toEntity()

        // 북마킹 여부 업데이트
        images.value = images.value?.map {
            if (it.imageUrl == entity.imageUrl) {
                it.copy(isBookmarked = bookmarked)
            } else {
                it
            }
        }

        if (isFromBus) {
            return
        }

        viewModelScope.launch {
            _event.emit(SearchEvent.UpdateBookmark(imageDocumentStatus, bookmarked))

            if (bookmarked) {
                if (!checkImageIsBookmarkedUseCase(entity.imageUrl)) {
                    insertBookmarkedImageUseCase(listOf(entity))
                }
            } else {
                deleteBookmarkedImageUseCase(entity)
            }
        }
    }

    private fun update(
        videoDocumentStatus: VideoDocumentStatus,
        bookmarked: Boolean,
        isFromBus: Boolean
    ) {
        val entity = videoDocumentStatus.toEntity()

        // 북마킹 여부 업데이트
        videos.value = videos.value?.map {
            if (it.url == entity.url) {
                it.copy(isBookmarked = bookmarked)
            } else {
                it
            }
        }

        if (isFromBus) {
            return
        }

        viewModelScope.launch {
            _event.emit(SearchEvent.UpdateBookmark(videoDocumentStatus, bookmarked))

            if (bookmarked) {
                if (!checkVideoIsBookmarkedUseCase(entity.url)) {
                    insertBookmarkedVideoUseCase(listOf(entity))
                }
            } else {
                deleteBookmarkedVideoUseCase(entity)
            }
        }
    }

    companion object {
        private const val DEBOUNCE_TIME = 300L
    }
}