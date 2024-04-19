package com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity

interface BookmarkRepository {

    suspend fun getAll(): List<ImageDocumentEntity>

    suspend fun isBookmarked(docUrl: String): Boolean

    suspend fun insertAll(imageDocumentEntities: List<ImageDocumentEntity>)

    suspend fun delete(imageDocumentEntity: ImageDocumentEntity)
}