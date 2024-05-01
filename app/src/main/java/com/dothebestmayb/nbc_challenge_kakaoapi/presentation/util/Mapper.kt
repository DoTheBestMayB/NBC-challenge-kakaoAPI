package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo

fun ImageDocumentEntity.toWithBookmarked(isBookmarked: Boolean) = MediaInfo.ImageDocumentStatus(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    dateTime = dateTime,
    isBookmarked = isBookmarked,
)

fun MediaInfo.ImageDocumentStatus.toEntity() = ImageDocumentEntity(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    dateTime = dateTime
)

fun VideoDocumentEntity.toWithBookmarked(isBookmarked: Boolean) = MediaInfo.VideoDocumentStatus(
    title = title,
    url = url,
    dateTime = dateTime,
    playTime = playTime,
    thumbnailUrl = thumbnail,
    author = author,
    isBookmarked = isBookmarked,
)

fun MediaInfo.VideoDocumentStatus.toEntity() = VideoDocumentEntity(
    title = title,
    url = url,
    dateTime = dateTime,
    playTime = playTime,
    thumbnail = thumbnailUrl,
    author = author,
)