package com.dothebestmayb.nbc_challenge_kakaoapi.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark.BookmarkEvent
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search.SearchEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SearchSharedViewModel: ViewModel() {
    private val _bookMarkEvents = MutableSharedFlow<SearchSharedEvent>()
    val bookMarkEvents = _bookMarkEvents.asSharedFlow()

    private val _searchMarkEvents = MutableSharedFlow<SearchSharedEvent>()
    val searchMarkEvents = _searchMarkEvents.asSharedFlow()

    fun updateEvent(event: SearchEvent.UpdateBookmark) = viewModelScope.launch {
        _bookMarkEvents.emit(SearchSharedEvent.UpdateBookmark(event.mediaInfo, event.bookmarked))
    }

    fun updateEvent(event: BookmarkEvent.UpdateBookmark) = viewModelScope.launch {
        _searchMarkEvents.emit(SearchSharedEvent.UpdateBookmark(event.mediaInfo, event.bookmarked))
    }
}