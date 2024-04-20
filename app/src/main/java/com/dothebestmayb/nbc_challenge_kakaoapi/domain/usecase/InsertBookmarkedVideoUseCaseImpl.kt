package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository

class InsertBookmarkedVideoUseCaseImpl(
    private val bookmarkRepository: BookmarkRepository
) : InsertBookmarkedVideoUseCase {
    override suspend fun invoke(videoDocumentEntities: List<VideoDocumentEntity>) {
        bookmarkRepository.insertAllVideo(videoDocumentEntities)
    }
}