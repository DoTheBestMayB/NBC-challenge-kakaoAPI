package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark.BookmarkViewModel

class BookmarkContainer(private val appContainer: AppContainer) {

    fun createSearchResultViewModelFactory(): AbstractSavedStateViewModelFactory {
        return object : AbstractSavedStateViewModelFactory() {
            val deleteBookmarkedImageUseCase = appContainer.createDeleteBookmarkedImageUseCase()
            val getAllBookmarkedImageUseCase = appContainer.createGetAllBookmarkedImageUseCase()
            val getAllBookmarkedVideoUseCase = appContainer.createGetAllBookmarkedVideoUseCase()
            val deleteBookmarkedVideoUseCase = appContainer.createDeleteBookmarkedVideoUseCase()

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return BookmarkViewModel(
                    deleteBookmarkedImageUseCase,
                    getAllBookmarkedImageUseCase,
                    getAllBookmarkedVideoUseCase,
                    deleteBookmarkedVideoUseCase,
                ) as T
            }
        }
    }

}
