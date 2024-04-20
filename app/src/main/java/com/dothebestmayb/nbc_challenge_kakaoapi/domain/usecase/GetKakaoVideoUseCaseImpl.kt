package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository

class GetKakaoVideoUseCaseImpl(
    private val kakaoSearchRepository: KakaoSearchRepository,
) : GetKakaoVideoUseCase {
    override suspend fun invoke(
        query: String,
        page: Int,
        size: Int
    ): NetworkResult<VideoSearchEntity> {
        return kakaoSearchRepository.getVideo(query, page, size)
    }
}