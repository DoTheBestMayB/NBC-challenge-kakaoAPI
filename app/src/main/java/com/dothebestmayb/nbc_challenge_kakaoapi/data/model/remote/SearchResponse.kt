package com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote

import com.squareup.moshi.Json

data class SearchResponse<T>(
    @Json(name = "meta") val metaResponse: MetaResponse,
    val documents: List<T>,
)