package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository

class CheckVideoIsBookmarkedUseCaseImpl(
    private val bookmarkRepository: BookmarkRepository,
) : CheckVideoIsBookmarkedUseCase {
    override suspend fun invoke(url: String): Boolean {
        return bookmarkRepository.isVideoBookmarked(url)
    }
}