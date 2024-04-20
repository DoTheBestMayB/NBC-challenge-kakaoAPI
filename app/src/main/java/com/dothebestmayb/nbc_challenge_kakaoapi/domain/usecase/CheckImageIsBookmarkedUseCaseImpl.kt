package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository

class CheckImageIsBookmarkedUseCaseImpl(
    private val bookmarkRepository: BookmarkRepository,
) : CheckImageIsBookmarkedUseCase {
    override suspend fun invoke(docUrl: String): Boolean {
        return bookmarkRepository.isImageBookmarked(docUrl)
    }
}