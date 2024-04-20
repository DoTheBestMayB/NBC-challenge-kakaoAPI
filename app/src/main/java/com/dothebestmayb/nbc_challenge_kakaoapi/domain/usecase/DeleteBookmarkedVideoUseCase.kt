package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity

interface DeleteBookmarkedVideoUseCase {

    suspend operator fun invoke(videoDocumentEntity: VideoDocumentEntity)
}