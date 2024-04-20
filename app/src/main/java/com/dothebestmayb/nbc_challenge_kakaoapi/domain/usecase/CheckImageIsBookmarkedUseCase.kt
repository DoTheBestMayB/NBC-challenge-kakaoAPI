package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

interface CheckImageIsBookmarkedUseCase {

    suspend operator fun invoke(docUrl: String): Boolean
}