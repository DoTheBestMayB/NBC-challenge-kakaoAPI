package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model

import java.util.Date

sealed interface MediaInfo {

    val dateTime: Date?
    val thumbnailUrl: String?

    data class ImageDocumentStatus(
        val collection: String,
        override val thumbnailUrl: String,
        val imageUrl: String,
        val width: Int,
        val height: Int,
        val displaySiteName: String,
        val docUrl: String,
        override val dateTime: Date,
        val isBookmarked: Boolean,
    ) : MediaInfo

    data class VideoDocumentStatus(
        val title: String,
        val url: String,
        override val dateTime: Date,
        val playTime: Int,
        override val thumbnailUrl: String,
        val author: String,
        val isBookmarked: Boolean,
    ) : MediaInfo
}