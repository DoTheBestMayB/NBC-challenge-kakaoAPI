package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository

class GetAllBookmarkedVideoUseCaseImpl(
    private val bookmarkRepository: BookmarkRepository,
) : GetAllBookmarkedVideoUseCase {
    override suspend fun invoke(): List<VideoDocumentEntity> {
        return bookmarkRepository.getAllVideo()
    }
}