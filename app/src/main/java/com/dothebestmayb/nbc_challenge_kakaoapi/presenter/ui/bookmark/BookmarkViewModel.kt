package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.BookmarkInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.bookmark.model.BookmarkItem
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.util.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {

    val items: StateFlow<List<BookmarkItem>> =
        bookmarkRepository.loadBookmarkedItem().distinctUntilChanged()
            .map { results ->
                results.map { info ->
                    when (info) {
                        is BookmarkInfo.Image -> info.toUi()
                        is BookmarkInfo.Video -> info.toUi()
                    }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = emptyList(),
            )

    fun onBookmarkClick(bookmarkItem: BookmarkItem) {
        viewModelScope.launch {
            bookmarkRepository.updateItem(bookmarkItem.switchBookmarkState().toDomain())
        }
    }
}
