package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity

interface InsertBookmarkedImageUseCase {

    suspend operator fun invoke(imageDocumentEntities: List<ImageDocumentEntity>)
}