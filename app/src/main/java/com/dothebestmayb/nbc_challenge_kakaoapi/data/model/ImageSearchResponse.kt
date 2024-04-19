package com.dothebestmayb.nbc_challenge_kakaoapi.data.model

import com.squareup.moshi.Json

data class ImageSearchResponse(
    @Json(name = "meta") val metaResponse: MetaResponse,
    val documents: List<ImageDocumentsResponse>,
)
