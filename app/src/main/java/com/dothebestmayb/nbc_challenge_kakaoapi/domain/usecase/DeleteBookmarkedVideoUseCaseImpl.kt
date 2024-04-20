package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository

class DeleteBookmarkedVideoUseCaseImpl(
    private val bookmarkRepository: BookmarkRepository,
) : DeleteBookmarkedVideoUseCase {
    override suspend fun invoke(videoDocumentEntity: VideoDocumentEntity) {
        return bookmarkRepository.deleteVideo(videoDocumentEntity)
    }
}