package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

interface CheckVideoIsBookmarkedUseCase {

    suspend operator fun invoke(url: String): Boolean
}