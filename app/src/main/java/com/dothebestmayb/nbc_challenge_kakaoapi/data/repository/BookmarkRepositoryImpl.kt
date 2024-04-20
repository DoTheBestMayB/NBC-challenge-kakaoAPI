package com.dothebestmayb.nbc_challenge_kakaoapi.data.repository

import com.dothebestmayb.nbc_challenge_kakaoapi.data.database.ImageDocumentDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.toData
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository

class BookmarkRepositoryImpl(
    private val dao: ImageDocumentDao,
) : BookmarkRepository {
    override suspend fun getAll(): List<ImageDocumentEntity> {
        return dao.getAll().map { it.toEntity() }
    }

    override suspend fun isBookmarked(docUrl: String): Boolean {
        return dao.isBookmarked(docUrl)
    }

    override suspend fun insertAll(imageDocumentEntities: List<ImageDocumentEntity>) {
        val documents = imageDocumentEntities.filter { dao.isBookmarked(it.docUrl).not() }.map { it.toData() }
        dao.insertAll(documents)
    }

    override suspend fun delete(imageDocumentEntity: ImageDocumentEntity) {
        dao.delete(imageDocumentEntity.toData())
    }
}