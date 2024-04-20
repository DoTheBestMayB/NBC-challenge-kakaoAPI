package com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote

import com.squareup.moshi.Json

data class VideoSearchResponse(
    @Json(name = "meta") val metaResponse: MetaResponse,
    val documents: List<VideoDocumentResponse>,
)
