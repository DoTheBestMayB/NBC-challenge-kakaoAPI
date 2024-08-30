package com.dothebestmayb.nbc_challenge_kakaoapi.domain.model

import java.time.LocalDateTime

sealed interface BookmarkInfo {
    data class Image(
        val thumbnailUrl: String,
        val imageUrl: String,
        val displaySiteName: String,
        val docUrl: String,
        val datetime: LocalDateTime,
        val searchKeyword: String,
        val bookmarked: Boolean,
    ) : BookmarkInfo

    data class Video(
        val title: String,
        val url: String,
        val datetime: LocalDateTime,
        val playTime: Int,
        val thumbnail: String,
        val searchKeyword: String,
        val bookmarked: Boolean,
    ) : BookmarkInfo
}