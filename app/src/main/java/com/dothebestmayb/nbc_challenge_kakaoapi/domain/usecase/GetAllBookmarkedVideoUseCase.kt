package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity

interface GetAllBookmarkedVideoUseCase {

    suspend operator fun invoke(): List<VideoDocumentEntity>
}