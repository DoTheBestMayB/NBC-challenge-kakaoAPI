package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import androidx.paging.PagingData
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.DocumentEntity
import kotlinx.coroutines.flow.Flow

interface GetKakaoImageUseCase {

    operator fun invoke(
        query: String,
        size: Int = DEFAULT_SIZE,
    ): Flow<PagingData<DocumentEntity>>


    companion object {
        private const val DEFAULT_SIZE = 30
    }
}