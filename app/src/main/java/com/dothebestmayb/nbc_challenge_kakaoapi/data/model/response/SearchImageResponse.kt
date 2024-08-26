package com.dothebestmayb.nbc_challenge_kakaoapi.data.model.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SearchImageResponse(
    val meta: Meta,
    val documents: List<Document>,
)