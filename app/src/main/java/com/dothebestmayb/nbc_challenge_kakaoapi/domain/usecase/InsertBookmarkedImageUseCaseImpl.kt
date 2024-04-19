package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository

class InsertBookmarkedImageUseCaseImpl(
    private val bookmarkRepository: BookmarkRepository
): InsertBookmarkedImageUseCase {
    override suspend fun invoke(imageDocumentEntities: List<ImageDocumentEntity>) {
        bookmarkRepository.insertAll(imageDocumentEntities)
    }
}