package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.DocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.CheckImageIsBookmarkedUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.CheckVideoIsBookmarkedUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.Event
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.debounce
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toWithBookmarked
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
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

    private val _results = MutableLiveData<Event<List<MediaInfo>>>()
    val results: LiveData<Event<List<MediaInfo>>>
        get() = _results

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _query = MutableLiveData<Event<String>>()
    val query: LiveData<Event<String>>
        get() = _query.debounce(DEBOUNCE_TIME)

    private var page = 1

    private val _event = MutableSharedFlow<SearchEvent>()
    val event = _event.asSharedFlow()

    fun search(query: String): Flow<PagingData<MediaInfo>> {
        return getKakaoImageUseCase(query)
            .map { pagingData ->
                pagingData.map { entity ->
                    when (entity) {
                        is ImageDocumentEntity -> entity.toWithBookmarked(checkImageIsBookmarkedUseCase(entity.imageUrl))
                        is VideoDocumentEntity -> entity.toWithBookmarked(checkVideoIsBookmarkedUseCase(entity.url))
                    }
//                    imageDocumentEntity.toWithBookmarked(
//                        checkImageIsBookmarkedUseCase(
//                            imageDocumentEntity.imageUrl
//                        )
//                    ) as MediaInfo
                }
            }
            .cachedIn(viewModelScope)
    }

//    fun search(query: String): Flow<PagingData<MediaInfo>> {
//        val imagesFlow = searchImageData(query)
//        val videosFlow = searchVideoData(query)
//
//        return combine(imagesFlow, videosFlow) { images, videos ->
//            images.map { image ->
//                image
//            }
//            videos.map { video ->
//                video
//            }
//        }
//    }

    fun fetchDataFromServer(query: String) {
        if (query.isBlank()) {
            _results.value = Event(emptyList())
            return
        }

        viewModelScope.launch {
            val videoResponse = async {
                getKakaoVideoUseCase(query, page)
            }

            val results = mutableListOf<MediaInfo>()

            val imageResponse = getKakaoImageUseCase(query, page)

//            imageResponse.onSuccess { imageSearchEntity ->
//                val result = imageSearchEntity.documents.map {
//                    it.toWithBookmarked(checkImageIsBookmarkedUseCase(it.imageUrl))
//                }
//                results.addAll(result)
//            }.onError { code, message ->
//                _error.value = "$code $message"
//            }.onException {
//                _error.value = "${it.message}"
//            }
//
//            videoResponse.await().onSuccess { videoSearchEntity ->
//                val result = videoSearchEntity.documents.map {
//                    it.toWithBookmarked(checkVideoIsBookmarkedUseCase(it.url))
//                }
//                results.addAll(result)
//            }.onError { code, message ->
//                _error.value = "$code $message"
//            }.onException {
//                _error.value = "${it.message}"
//            }
//            _results.value = Event(results.sortedByDescending { it.dateTime })
        }
    }

    fun updateQuery(query: String) {
        _query.value = Event(query)
    }

    fun updateBookmarkState(mediaInfo: MediaInfo, bookmarked: Boolean, isFromBus: Boolean) {
        updateItem(mediaInfo, bookmarked)
        deliverEvent(mediaInfo, bookmarked, isFromBus)
        when (mediaInfo) {
            is MediaInfo.ImageDocumentStatus -> updateDB(mediaInfo, bookmarked)
            is MediaInfo.VideoDocumentStatus -> updateDB(mediaInfo, bookmarked)
        }
    }

    private fun updateItem(mediaInfo: MediaInfo, bookmarked: Boolean) {
        val results = _results.value?.peekContent()?.map {
            if (it.thumbnailUrl == mediaInfo.thumbnailUrl) {
                when (it) {
                    is MediaInfo.ImageDocumentStatus -> it.copy(isBookmarked = bookmarked)
                    is MediaInfo.VideoDocumentStatus -> it.copy(isBookmarked = bookmarked)
                }
            } else {
                it
            }
        } ?: emptyList()
        _results.value = Event(results)
    }

    private fun deliverEvent(mediaInfo: MediaInfo, bookmarked: Boolean, isFromBus: Boolean) =
        viewModelScope.launch {
            if (isFromBus) {
                return@launch
            }
            _event.emit(SearchEvent.UpdateBookmark(mediaInfo, bookmarked))
        }

    private fun updateDB(
        imageDocumentStatus: MediaInfo.ImageDocumentStatus,
        bookmarked: Boolean,
    ) = viewModelScope.launch {
        val entity = imageDocumentStatus.toEntity()

        if (bookmarked) {
            if (!checkImageIsBookmarkedUseCase(entity.imageUrl)) {
                insertBookmarkedImageUseCase(listOf(entity))
            }
        } else {
            deleteBookmarkedImageUseCase(entity)
        }
    }

    private fun updateDB(
        videoDocumentStatus: MediaInfo.VideoDocumentStatus,
        bookmarked: Boolean,
    ) = viewModelScope.launch {
        val entity = videoDocumentStatus.toEntity()

        if (bookmarked) {
            if (!checkVideoIsBookmarkedUseCase(entity.url)) {
                insertBookmarkedVideoUseCase(listOf(entity))
            }
        } else {
            deleteBookmarkedVideoUseCase(entity)
        }
    }

    companion object {
        private const val DEBOUNCE_TIME = 300L
    }
}