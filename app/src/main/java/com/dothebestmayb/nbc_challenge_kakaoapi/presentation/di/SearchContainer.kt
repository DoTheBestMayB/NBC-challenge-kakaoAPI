package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dothebestmayb.nbc_challenge_kakaoapi.data.repository.KakaoSearchRepositoryImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoImageUseCaseImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search.SearchViewModel

class SearchContainer(private val appContainer: AppContainer) {

    fun createKakaoSearchRepository(): KakaoSearchRepository {
        return KakaoSearchRepositoryImpl(
            appContainer.createKakaoRemoteDataSource()
        )
    }

    fun createGetKakaoImageUseCase(): GetKakaoImageUseCase {
        return GetKakaoImageUseCaseImpl(createKakaoSearchRepository())
    }

    fun createSearchResultViewModelFactory(): AbstractSavedStateViewModelFactory {
        return object : AbstractSavedStateViewModelFactory() {
            val getKakaoImageUseCase = createGetKakaoImageUseCase()
            val checkMediaIsBookmarkedUseCase = appContainer.createCheckMediaIsBookmarkedUseCase()
            val deleteBookmarkedImageUseCase = appContainer.createDeleteBookmarkedImageUseCase()
            val insertBookmarkedImageUseCase = appContainer.createInsertBookmarkedImageUseCase()

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return SearchViewModel(
                    getKakaoImageUseCase,
                    checkMediaIsBookmarkedUseCase,
                    deleteBookmarkedImageUseCase,
                    insertBookmarkedImageUseCase,
                ) as T
            }
        }
    }
}