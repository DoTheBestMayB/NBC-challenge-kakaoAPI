package com.dothebestmayb.nbc_challenge_kakaoapi.domain.model

import java.time.LocalDateTime

data class ImageSearchInfo(
    val thumbnailUrl: String,
    val imageUrl: String,
    val displaySiteName: String,
    val docUrl: String,
    val datetime: LocalDateTime,
)
