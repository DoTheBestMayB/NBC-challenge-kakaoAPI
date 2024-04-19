package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository

class CheckMediaIsBookmarkedUseCaseImpl(
    private val bookmarkRepository: BookmarkRepository,
): CheckMediaIsBookmarkedUseCase {
    override suspend fun invoke(docUrl: String): Boolean {
        return bookmarkRepository.isBookmarked(docUrl)
    }
}