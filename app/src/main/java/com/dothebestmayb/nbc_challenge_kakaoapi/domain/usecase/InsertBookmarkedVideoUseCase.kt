package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity

interface InsertBookmarkedVideoUseCase {

    suspend operator fun invoke(videoDocumentEntities: List<VideoDocumentEntity>)
}