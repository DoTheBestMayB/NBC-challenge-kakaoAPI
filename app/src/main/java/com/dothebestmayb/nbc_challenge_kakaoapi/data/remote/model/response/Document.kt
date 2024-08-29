package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response

import com.squareup.moshi.Json
import java.time.LocalDateTime

data class Document(
    val collection: String,
    @Json(name = "thumbnail_url") val thumbnailUrl: String,
    @Json(name = "image_url") val imageUrl: String,
    val width: Int,
    val height: Int,
    @Json(name = "display_sitename") val displaySiteName: String,
    @Json(name = "doc_url") val docUrl: String,
    val datetime: LocalDateTime,
)