package com.dothebestmayb.nbc_challenge_kakaoapi.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean,
)