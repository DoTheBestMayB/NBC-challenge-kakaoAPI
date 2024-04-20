package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoSearchEntity

interface GetKakaoVideoUseCase {

    suspend operator fun invoke(
        query: String,
        page: Int,
        size: Int = DEFAULT_SIZE,
    ): NetworkResult<VideoSearchEntity>

    companion object {
        private const val DEFAULT_SIZE = 10
    }
}