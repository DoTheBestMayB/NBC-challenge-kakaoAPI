package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository

class GetKakaoImageUseCaseImpl(
    private val kakaoSearchRepository: KakaoSearchRepository,
) : GetKakaoImageUseCase {
    override suspend fun invoke(
        query: String,
        page: Int,
        size: Int
    ): NetworkResult<ImageSearchEntity> {
        return kakaoSearchRepository.getImage(query, page, size)
    }
}