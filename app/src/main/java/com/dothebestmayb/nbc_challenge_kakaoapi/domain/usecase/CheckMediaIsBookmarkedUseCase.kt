package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

interface CheckMediaIsBookmarkedUseCase {

    suspend operator fun invoke(docUrl: String): Boolean
}