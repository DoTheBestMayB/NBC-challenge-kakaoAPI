package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.ImageDocumentStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.VideoDocumentStatus

fun ImageDocumentEntity.toWithBookmarked(isBookmarked: Boolean) = ImageDocumentStatus(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    datetime = datetime,
    isBookmarked = isBookmarked,
)

fun ImageDocumentStatus.toEntity() = ImageDocumentEntity(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    datetime = datetime
)

fun VideoDocumentEntity.toWithBookmarked(isBookmarked: Boolean) = VideoDocumentStatus(
    title = title,
    url = url,
    datetime = datetime,
    playTime = playTime,
    thumbnail = thumbnail,
    author = author,
    isBookmarked = isBookmarked,
)

fun VideoDocumentStatus.toEntity() = VideoDocumentEntity(
    title = title,
    url = url,
    datetime = datetime,
    playTime = playTime,
    thumbnail = thumbnail,
    author = author,
)