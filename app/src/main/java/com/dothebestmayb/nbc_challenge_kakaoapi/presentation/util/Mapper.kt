package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.ImageDocumentStatus

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