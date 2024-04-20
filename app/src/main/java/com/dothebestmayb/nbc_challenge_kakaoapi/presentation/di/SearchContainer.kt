package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dothebestmayb.nbc_challenge_kakaoapi.data.repository.KakaoSearchRepositoryImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoImageUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoImageUseCaseImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoVideoUseCase
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase.GetKakaoVideoUseCaseImpl
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

    fun createGetKakaoVideoUseCase(): GetKakaoVideoUseCase {
        return GetKakaoVideoUseCaseImpl(createKakaoSearchRepository())
    }

    fun createSearchResultViewModelFactory(): AbstractSavedStateViewModelFactory {
        return object : AbstractSavedStateViewModelFactory() {
            val getKakaoImageUseCase = createGetKakaoImageUseCase()
            val getKakaoVideoUseCase = createGetKakaoVideoUseCase()
            val checkImageIsBookmarkedUseCase = appContainer.createCheckImageIsBookmarkedUseCase()
            val deleteBookmarkedImageUseCase = appContainer.createDeleteBookmarkedImageUseCase()
            val insertBookmarkedImageUseCase = appContainer.createInsertBookmarkedImageUseCase()
            val checkVideoIsBookmarkedUseCase = appContainer.createCheckVideoIsBookmarkedUseCase()
            val deleteBookmarkedVideoUseCase = appContainer.createDeleteBookmarkedVideoUseCase()
            val insertBookmarkedVideoUseCase = appContainer.createInsertBookmarkedVideoUseCase()

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return SearchViewModel(
                    getKakaoImageUseCase,
                    getKakaoVideoUseCase,
                    checkImageIsBookmarkedUseCase,
                    deleteBookmarkedImageUseCase,
                    insertBookmarkedImageUseCase,
                    checkVideoIsBookmarkedUseCase,
                    deleteBookmarkedVideoUseCase,
                    insertBookmarkedVideoUseCase,
                ) as T
            }
        }
    }
}