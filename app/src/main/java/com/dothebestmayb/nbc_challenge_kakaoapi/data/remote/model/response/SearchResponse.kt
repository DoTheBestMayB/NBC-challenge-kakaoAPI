package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SearchResponse<out T : SearchDocumentResponse>(
    val meta: Meta,
    val documents: List<T>,
)