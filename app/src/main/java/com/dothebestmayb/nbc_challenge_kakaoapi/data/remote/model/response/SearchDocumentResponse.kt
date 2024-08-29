package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response

import com.squareup.moshi.Json
import java.time.LocalDateTime

sealed interface SearchDocumentResponse {

    data class ImageDocument(
        val collection: String,
        @Json(name = "thumbnail_url") val thumbnailUrl: String,
        @Json(name = "image_url") val imageUrl: String,
        val width: Int,
        val height: Int,
        @Json(name = "display_sitename") val displaySiteName: String,
        @Json(name = "doc_url") val docUrl: String,
        val datetime: LocalDateTime,
    ) : SearchDocumentResponse

    data class VideoDocument(
        val title: String,
        val url: String,
        val datetime: LocalDateTime,
        @Json(name = "play_time") val playTime: Int,
        @Json(name = "thumbnail") val thumbnail: String,
        val author: String,
    ) : SearchDocumentResponse
}