package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetAllBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetAllBookmarkedVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.BookmarkingEvent
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.ImageDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.VideoDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search.SearchEventHandler
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toWithBookmarked
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val deleteBookmarkedImageUseCase: DeleteBookmarkedImageUseCase,
    private val getAllBookmarkedImageUseCase: GetAllBookmarkedImageUseCase,
    private val insertBookmarkedImageUseCase: InsertBookmarkedImageUseCase,
    private val getAllBookmarkedVideoUseCase: GetAllBookmarkedVideoUseCase,
    private val deleteBookmarkedVideoUseCase: DeleteBookmarkedVideoUseCase,
    private val insertBookmarkedVideoUseCase: InsertBookmarkedVideoUseCase,
) : ViewModel() {

    private val _bookmarkedImageDocuments = MutableLiveData<List<ImageDocumentStatus>>()
    val bookmarkedImageDocuments: LiveData<List<ImageDocumentStatus>>
        get() = _bookmarkedImageDocuments

    private val _bookmarkedVideoDocuments = MutableLiveData<List<VideoDocumentStatus>>()
    val bookmarkedVideoDocuments: LiveData<List<VideoDocumentStatus>>
        get() = _bookmarkedVideoDocuments

    private val _bookmarkedDocuments = MediatorLiveData<List<MediaInfo>>().apply {
        addSource(bookmarkedImageDocuments) {
            value = it as List<MediaInfo> + (bookmarkedVideoDocuments.value
                ?: emptyList()) as List<MediaInfo>
        }
        addSource(bookmarkedVideoDocuments) {
            value = (bookmarkedImageDocuments.value
                ?: emptyList()) as List<MediaInfo> + it as List<MediaInfo>
        }
    }
    val bookmarkedDocuments: LiveData<List<MediaInfo>>
        get() = _bookmarkedDocuments

    private lateinit var bookmarkEventHandler: BookmarkEventHandler
    private lateinit var searchEventHandler: SearchEventHandler

    fun registerEventBus(
        bookmarkEventHandler: BookmarkEventHandler,
        searchEventHandler: SearchEventHandler
    ) {
        this.bookmarkEventHandler = bookmarkEventHandler
        this.searchEventHandler = searchEventHandler
    }

    fun fetch() {
        viewModelScope.launch {
            _bookmarkedImageDocuments.value =
                getAllBookmarkedImageUseCase().map { it.toWithBookmarked(true) }
            _bookmarkedVideoDocuments.value =
                getAllBookmarkedVideoUseCase().map { it.toWithBookmarked(true) }
        }
    }

    fun remove(mediaInfo: MediaInfo, isUpdateAtRoom: Boolean = true) {
        when (mediaInfo) {
            is ImageDocumentStatus -> remove(mediaInfo, isUpdateAtRoom)
            is VideoDocumentStatus -> remove(mediaInfo, isUpdateAtRoom)
        }
    }

    private fun remove(item: ImageDocumentStatus, isUpdateAtRoom: Boolean = true) {
        if (isUpdateAtRoom) {
            viewModelScope.launch {
                searchEventHandler.postEvent(BookmarkingEvent(item, false))

                deleteBookmarkedImageUseCase(item.toEntity())
            }
        }

        val values = _bookmarkedImageDocuments.value?.toMutableList() ?: return
        values.removeIf {
            it.docUrl == item.docUrl
        }
        _bookmarkedImageDocuments.value = values
    }

    private fun remove(item: VideoDocumentStatus, isUpdateAtRoom: Boolean = true) {
        if (isUpdateAtRoom) {
            viewModelScope.launch {
                searchEventHandler.postEvent(BookmarkingEvent(item, false))

                deleteBookmarkedVideoUseCase(item.toEntity())
            }
        }
        val values = _bookmarkedVideoDocuments.value?.toMutableList() ?: return
        values.removeIf {
            it.url == item.url
        }
        _bookmarkedVideoDocuments.value = values
    }

    fun update(bookmarkingEvent: BookmarkingEvent) {
        if (bookmarkingEvent.bookmarked) {
            add(bookmarkingEvent.mediaInfo, false)
        } else {
            remove(bookmarkingEvent.mediaInfo, false)
        }
    }

    fun add(mediaInfo: MediaInfo, isUpdateAtRoom: Boolean = true) {
        when (mediaInfo) {
            is ImageDocumentStatus -> add(mediaInfo, isUpdateAtRoom)
            is VideoDocumentStatus -> add(mediaInfo, isUpdateAtRoom)
        }
    }

    private fun add(item: ImageDocumentStatus, isUpdateAtRoom: Boolean = true) {
        if (isUpdateAtRoom) {
            viewModelScope.launch {
                insertBookmarkedImageUseCase(listOf(item.toEntity()))
            }
        }
        val values = _bookmarkedImageDocuments.value?.toMutableList() ?: return
        val idx = values.indexOfFirst { it.docUrl == item.docUrl }
        if (idx == -1) {
            values.add(item)
        } else {
            values[idx] = item
        }
        _bookmarkedImageDocuments.value = values
    }

    private fun add(item: VideoDocumentStatus, isUpdateAtRoom: Boolean = true) {
        if (isUpdateAtRoom) {
            viewModelScope.launch {
                insertBookmarkedVideoUseCase(listOf(item.toEntity()))
            }
        }
        val values = _bookmarkedVideoDocuments.value?.toMutableList() ?: return
        val idx = values.indexOfFirst { it.url == item.url }
        if (idx == -1) {
            values.add(item)
        } else {
            values[idx] = item
        }
        _bookmarkedVideoDocuments.value = values
    }
}