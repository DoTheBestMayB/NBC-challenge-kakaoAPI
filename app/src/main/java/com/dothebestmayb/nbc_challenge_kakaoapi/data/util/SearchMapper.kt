package com.dothebestmayb.nbc_challenge_kakaoapi.data.util

import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.ImageDocumentsResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.ImageSearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.MetaResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentsEntity
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

fun ImageDocumentsResponse.toEntity() = ImageDocumentsEntity(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    datetime = datetime
)