package com.dothebestmayb.nbc_challenge_kakaoapi.data.util

import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.local.ImageDocument
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.local.VideoDocument
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.ImageDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.MetaResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.SearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.VideoDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.MetaEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoSearchEntity

fun SearchResponse<ImageDocumentResponse>.toEntity() = ImageSearchEntity(
    metaResponse = metaResponse.toEntity(),
    documents = documents.map {
        it.toEntity()
    }
)

fun SearchResponse<VideoDocumentResponse>.toEntity() = VideoSearchEntity(
    metaResponse = metaResponse.toEntity(),
    documents = documents.map {
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
    dateTime = datetime
)

fun ImageDocument.toEntity() = ImageDocumentEntity(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    dateTime = datetime
)

fun ImageDocumentEntity.toData() = ImageDocument(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    datetime = dateTime
)

fun VideoDocumentResponse.toEntity() = VideoDocumentEntity(
    title = title,
    url = url,
    dateTime = datetime,
    playTime = playTime,
    thumbnail = thumbnail,
    author = author
)

fun VideoDocument.toEntity() = VideoDocumentEntity(
    title = title,
    url = url,
    dateTime = datetime,
    playTime = playTime,
    thumbnail = thumbnail,
    author = author
)

fun VideoDocumentEntity.toData() = VideoDocument(
    title = title,
    url = url,
    datetime = dateTime,
    playTime = playTime,
    thumbnail = thumbnail,
    author = author
)