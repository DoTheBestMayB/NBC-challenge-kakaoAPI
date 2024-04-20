package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity

interface GetAllBookmarkedImageUseCase {

    suspend operator fun invoke(): List<ImageDocumentEntity>
}