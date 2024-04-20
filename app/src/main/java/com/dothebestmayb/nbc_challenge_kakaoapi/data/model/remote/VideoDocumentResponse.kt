package com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote

import com.squareup.moshi.Json
import java.util.Date

data class VideoDocumentResponse(
    val title: String,
    val url: String,
    val datetime: Date,
    @Json(name = "play_time") val playTime: Int,
    val thumbnail: String,
    val author: String,
)
