package com.dothebestmayb.nbc_challenge_kakaoapi.data.repository

import com.dothebestmayb.nbc_challenge_kakaoapi.data.database.ImageDocumentDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.database.VideoDocumentDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.toData
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.DocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository

class BookmarkRepositoryImpl(
    private val imageDao: ImageDocumentDao,
    private val videoDao: VideoDocumentDao,
) : BookmarkRepository {
    override suspend fun getAllImage(): List<ImageDocumentEntity> {
        return imageDao.getAll().map { it.toEntity() }
    }

    override suspend fun getAllVideo(): List<VideoDocumentEntity> {
        return videoDao.getAll().map { it.toEntity() }
    }

    override suspend fun isImageBookmarked(imageUrl: String): Boolean {
        return imageDao.isBookmarked(imageUrl)
    }

    override suspend fun isVideoBookmarked(url: String): Boolean {
        return videoDao.isBookmarked(url)
    }

    override suspend fun insertAllImage(imageDocumentEntities: List<ImageDocumentEntity>) {
        val documents =
            imageDocumentEntities.filter { imageDao.isBookmarked(it.docUrl).not() }
                .map { it.toData() }
        imageDao.insertAll(documents)
    }

    override suspend fun insertAllVideo(videoDocumentEntities: List<VideoDocumentEntity>) {
        val documents =
            videoDocumentEntities.filter { videoDao.isBookmarked(it.url).not() }.map { it.toData() }
        videoDao.insertAll(documents)
    }

    override suspend fun deleteImage(imageDocumentEntity: ImageDocumentEntity) {
        imageDao.delete(imageDocumentEntity.toData())
    }

    override suspend fun deleteVideo(videoDocumentEntity: VideoDocumentEntity) {
        videoDao.delete(videoDocumentEntity.toData())
    }
}