package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.onError
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.onException
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.onSuccess
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentsEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.debounce
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getKakaoImageUseCase: GetKakaoImageUseCase,
) : ViewModel() {

    private val _images = MutableLiveData<List<ImageDocumentsEntity>>()
    val images: LiveData<List<ImageDocumentsEntity>>
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
                _images.value = imageSearchEntity.documents
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

    companion object {
        private const val DEBOUNCE_TIME = 300L
    }
}