package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository

class DeleteBookmarkedImageUseCaseImpl(
    private val bookmarkRepository: BookmarkRepository,
) : DeleteBookmarkedImageUseCase {
    override suspend fun invoke(imageDocumentEntity: ImageDocumentEntity) {
        bookmarkRepository.deleteImage(imageDocumentEntity)
    }
}