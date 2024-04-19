package com.dothebestmayb.nbc_challenge_kakaoapi.data.util

import com.dothebestmayb.nbc_challenge_kakaoapi.data.database.ImageDocument
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.ImageDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.ImageSearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.MetaResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.MetaEntity

fun ImageSearchResponse.toEntity() = ImageSearchEntity(
    metaResponse = this.metaResponse.toEntity(),
    documents = this.documents.map {
        it.toEntity()
    }
)

fun MetaResponse.toEntity() = MetaEntity(
    totalCount = totalCount,
    pageableCount = pageableCount,
    isEnd = isEnd,
)

fun ImageDocumentResponse.toEntity() = ImageDocumentEntity(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    datetime = datetime
)

fun ImageDocument.toEntity() = ImageDocumentEntity(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    datetime = datetime
)

fun ImageDocumentEntity.toData() = ImageDocument(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    datetime = datetime

)