package com.dothebestmayb.nbc_challenge_kakaoapi.data.model.response

import java.time.LocalDateTime

data class Document(
    val collection: String,
    val thumbnailUrl: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val displaySiteName: String,
    val docUrl: String,
    val datetime: LocalDateTime,
)