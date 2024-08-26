package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model

import androidx.annotation.IntRange
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetImageRequest(
    val query: String,
    val sort: SortType = SortType.ACCURACY,
    @IntRange(1, 50) val page: Int = 1,
    @IntRange(1, 80) val size: Int = 80,
)