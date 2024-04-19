package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository

class GetAllBookmarkedImageUseCaseImpl(
    private val bookmarkRepository: BookmarkRepository
) : GetAllBookmarkedImageUseCase {
    override suspend fun invoke(): List<ImageDocumentEntity> {
        return bookmarkRepository.getAll()
    }
}