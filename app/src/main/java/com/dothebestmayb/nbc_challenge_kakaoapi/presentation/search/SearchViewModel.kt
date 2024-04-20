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
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark.BookmarkEventHandler
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.BookmarkingEvent
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.ImageDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.VideoDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.debounce
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toWithBookmarked
import kotlinx.coroutines.async
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

    private val _images = MutableLiveData<List<ImageDocumentStatus>>()
    val images: LiveData<List<ImageDocumentStatus>>
        get() = _images

    private val _videos = MutableLiveData<List<VideoDocumentStatus>>()
    val videos: LiveData<List<VideoDocumentStatus>>
        get() = _videos

    private val _results = MediatorLiveData<List<MediaInfo>>().apply {
        addSource(images) {
            value = it as List<MediaInfo> + (videos.value ?: emptyList()) as List<MediaInfo>
        }
        addSource(videos) {
            value = (images.value ?: emptyList()) as List<MediaInfo> + it as List<MediaInfo>
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

    private lateinit var bookmarkEventHandler: BookmarkEventHandler
    private lateinit var searchEventHandler: SearchEventHandler

    fun registerEventBus(
        bookmarkEventHandler: BookmarkEventHandler,
        searchEventHandler: SearchEventHandler
    ) {
        this.bookmarkEventHandler = bookmarkEventHandler
        this.searchEventHandler = searchEventHandler
    }

    fun fetchDataFromServer() {
        val query = _query.value

        if (query.isNullOrBlank()) {
            _images.value = emptyList()
            _videos.value = emptyList()
            return
        }

        viewModelScope.launch {
            val videoResponse = async {
                getKakaoVideoUseCase(query, page)
            }

            val imageResponse = getKakaoImageUseCase(query, page)

            imageResponse.onSuccess { imageSearchEntity ->
                val result = imageSearchEntity.documents.map {
                    it.toWithBookmarked(checkImageIsBookmarkedUseCase(it.docUrl))
                }
                _images.value = result
            }.onError { code, message ->
                _error.value = "$code $message"
            }.onException {
                _error.value = "${it.message}"
            }

            videoResponse.await().onSuccess { videoSearchEntity ->
                val result = videoSearchEntity.documents.map {
                    it.toWithBookmarked(checkVideoIsBookmarkedUseCase(it.url))
                }
                _videos.value = result
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
        }
    }

    private fun update(
        imageDocumentStatus: ImageDocumentStatus,
        bookmarked: Boolean,
        isFromBus: Boolean
    ) {
        val entity = imageDocumentStatus.toEntity()

        // 북마킹 여부 업데이트
        _images.value = _images.value?.map {
            if (it.docUrl == entity.docUrl) {
                it.copy(isBookmarked = bookmarked)
            } else {
                it
            }
        }

        if (isFromBus) {
            return
        }

        viewModelScope.launch {
            bookmarkEventHandler.postEvent(BookmarkingEvent(imageDocumentStatus, bookmarked))

            if (bookmarked) {
                if (!checkImageIsBookmarkedUseCase(entity.docUrl)) {
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
        _videos.value = _videos.value?.map {
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
            bookmarkEventHandler.postEvent(BookmarkingEvent(videoDocumentStatus, bookmarked))

            if (bookmarked) {
                if (!checkImageIsBookmarkedUseCase(entity.url)) {
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