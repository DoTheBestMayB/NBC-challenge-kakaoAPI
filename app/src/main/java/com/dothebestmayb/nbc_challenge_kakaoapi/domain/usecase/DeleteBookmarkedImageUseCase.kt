package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity

interface DeleteBookmarkedImageUseCase {

    suspend operator fun invoke(imageDocumentEntity: ImageDocumentEntity)
}