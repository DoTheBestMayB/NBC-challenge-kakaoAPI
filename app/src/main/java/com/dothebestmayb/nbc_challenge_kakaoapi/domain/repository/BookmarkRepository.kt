package com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity

interface BookmarkRepository {

    suspend fun getAllImage(): List<ImageDocumentEntity>
    suspend fun getAllVideo(): List<VideoDocumentEntity>

    suspend fun isImageBookmarked(imageUrl: String): Boolean
    suspend fun isVideoBookmarked(url: String): Boolean

    suspend fun insertAllImage(imageDocumentEntities: List<ImageDocumentEntity>)
    suspend fun insertAllVideo(videoDocumentEntities: List<VideoDocumentEntity>)

    suspend fun deleteImage(imageDocumentEntity: ImageDocumentEntity)
    suspend fun deleteVideo(videoDocumentEntity: VideoDocumentEntity)
}