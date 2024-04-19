package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dothebestmayb.nbc_challenge_kakaoapi.data.repository.BookmarkRepositoryImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.CheckMediaIsBookmarkedUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.CheckMediaIsBookmarkedUseCaseImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.DeleteBookmarkedImageUseCaseImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetAllBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetAllBookmarkedImageUseCaseImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.InsertBookmarkedImageUseCaseImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark.BookmarkViewModel

class BookmarkContainer(private val appContainer: AppContainer) {

    fun createSearchResultViewModelFactory(): AbstractSavedStateViewModelFactory {
        return object : AbstractSavedStateViewModelFactory() {
            val checkMediaIsBookmarkedUseCase = appContainer.createCheckMediaIsBookmarkedUseCase()
            val deleteBookmarkedImageUseCase = appContainer.createDeleteBookmarkedImageUseCase()
            val getAllBookmarkedImageUseCase = appContainer.createGetAllBookmarkedImageUseCase()
            val insertBookmarkedImageUseCase = appContainer.createInsertBookmarkedImageUseCase()

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return BookmarkViewModel(
                    checkMediaIsBookmarkedUseCase,
                    deleteBookmarkedImageUseCase,
                    getAllBookmarkedImageUseCase,
                    insertBookmarkedImageUseCase,
                ) as T
            }
        }
    }

}
