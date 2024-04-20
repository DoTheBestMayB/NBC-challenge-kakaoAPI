package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model

import java.util.Date

data class ImageDocumentStatus(
    val collection: String,
    val thumbnailUrl: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val displaySiteName: String,
    val docUrl: String,
    val datetime: Date,
    val isBookmarked: Boolean,
) : MediaInfo