package com.dothebestmayb.nbc_challenge_kakaoapi.domain.model

import java.util.Date

data class ImageDocumentsEntity(
    val collection: String,
    val thumbnailUrl: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val displaySiteName: String,
    val docUrl: String,
    val datetime: Date,
) : MediaInfo