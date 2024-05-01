package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetAllBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetAllBookmarkedVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.Event
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.toWithBookmarked
import com.dothebestmayb.nbc_challenge_kakaoapi.shared.SearchSharedEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val deleteBookmarkedImageUseCase: DeleteBookmarkedImageUseCase,
    private val getAllBookmarkedImageUseCase: GetAllBookmarkedImageUseCase,
    private val getAllBookmarkedVideoUseCase: GetAllBookmarkedVideoUseCase,
    private val deleteBookmarkedVideoUseCase: DeleteBookmarkedVideoUseCase,
) : ViewModel() {

    private val _bookmarkedDocuments = MutableLiveData<Event<List<MediaInfo>>>()
    val bookmarkedDocuments: LiveData<Event<List<MediaInfo>>>
        get() = _bookmarkedDocuments

    private val _event = MutableSharedFlow<BookmarkEvent>()
    val event = _event.asSharedFlow()

    fun fetch() {
        viewModelScope.launch {
            val images = async {
                getAllBookmarkedImageUseCase().map { it.toWithBookmarked(true) }
            }
            val videos = getAllBookmarkedVideoUseCase().map { it.toWithBookmarked(true) }
            _bookmarkedDocuments.value = Event(images.await() + videos)
        }
    }

    fun remove(mediaInfo: MediaInfo, isUpdateAtRoom: Boolean = true) = viewModelScope.launch {
        val values = _bookmarkedDocuments.value?.peekContent()?.filter {
            it.thumbnailUrl != mediaInfo.thumbnailUrl
        } ?: emptyList()
        _bookmarkedDocuments.value = Event(values)

        if (isUpdateAtRoom.not()) {
            return@launch
        }

        when (mediaInfo) {
            is MediaInfo.ImageDocumentStatus -> run {
                _event.emit(BookmarkEvent.UpdateBookmark(mediaInfo, false))
                deleteBookmarkedImageUseCase(mediaInfo.toEntity())
            }

            is MediaInfo.VideoDocumentStatus -> run {
                _event.emit(BookmarkEvent.UpdateBookmark(mediaInfo, false))
                deleteBookmarkedVideoUseCase(mediaInfo.toEntity())
            }
        }
    }

    fun update(searchSharedEvent: SearchSharedEvent.UpdateBookmark) {
        if (searchSharedEvent.bookmarked) {
            add(searchSharedEvent.mediaInfo)
        } else {
            remove(searchSharedEvent.mediaInfo, false)
        }
    }

    private fun add(mediaInfo: MediaInfo) = viewModelScope.launch {
        var isFind = false
        val values = _bookmarkedDocuments.value?.peekContent()?.map {
            if (it.thumbnailUrl == mediaInfo.thumbnailUrl) {
                isFind = true
                mediaInfo
            } else {
                it
            }
        }?.toMutableList() ?: mutableListOf()

        if (isFind.not()) {
            values.add(mediaInfo)
        }
        _bookmarkedDocuments.value = Event(values)
    }
}