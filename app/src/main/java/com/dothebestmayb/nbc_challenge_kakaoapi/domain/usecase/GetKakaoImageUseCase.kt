package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult

interface GetKakaoImageUseCase {

    suspend operator fun invoke(
        query: String,
        page: Int,
        size: Int = DEFAULT_SIZE,
    ): NetworkResult<ImageSearchEntity>


    companion object {
        private const val DEFAULT_SIZE = 10
    }
}