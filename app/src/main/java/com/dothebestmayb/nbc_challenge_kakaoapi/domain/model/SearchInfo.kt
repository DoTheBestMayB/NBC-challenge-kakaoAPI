package com.dothebestmayb.nbc_challenge_kakaoapi.domain.model

import java.time.LocalDateTime

sealed interface SearchInfo {
    data class ImageSearchInfo(
        val thumbnailUrl: String,
        val imageUrl: String,
        val displaySiteName: String,
        val docUrl: String,
        val datetime: LocalDateTime,
        val searchKeyword: String,
        val bookmarked: Boolean,
    ) : SearchInfo

    data class VideoSearchInfo(
        val title: String,
        val url: String,
        val datetime: LocalDateTime,
        val playTime: Int,
        val thumbnail: String,
        val searchKeyword: String,
        val bookmarked: Boolean,
    ) : SearchInfo
}