package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import androidx.paging.PagingData
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.DocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import kotlinx.coroutines.flow.Flow

class GetKakaoImageUseCaseImpl(
    private val kakaoSearchRepository: KakaoSearchRepository,
) : GetKakaoImageUseCase {
    override fun invoke(
        query: String,
        size: Int
    ): Flow<PagingData<DocumentEntity>> {
        return kakaoSearchRepository.getImage(query, size)
    }
}