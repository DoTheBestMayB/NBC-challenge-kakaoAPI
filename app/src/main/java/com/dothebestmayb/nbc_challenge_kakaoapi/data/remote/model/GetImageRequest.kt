package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model

import androidx.annotation.IntRange
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetImageRequest(
    val query: String,
    val sort: SortType = SortType.ACCURACY,
    @IntRange(MIN_PAGE_INDEX, MAX_PAGE_INDEX) val page: Int = DEFAULT_PAGE_INDEX,
    @IntRange(MIN_DATA_SIZE, MAX_DATA_SIZE) val size: Int = DEFAULT_DATA_SIZE,
) {
    companion object {
        private const val MIN_PAGE_INDEX = 1L
        private const val MAX_PAGE_INDEX = 50L
        private const val DEFAULT_PAGE_INDEX = 1

        private const val MIN_DATA_SIZE = 1L
        private const val MAX_DATA_SIZE = 80L
        private const val DEFAULT_DATA_SIZE = 80
    }
}