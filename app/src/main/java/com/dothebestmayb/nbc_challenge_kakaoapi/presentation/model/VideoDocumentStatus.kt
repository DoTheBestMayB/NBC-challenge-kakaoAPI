package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model

import java.util.Date

data class VideoDocumentStatus(
    val title: String,
    val url: String,
    val datetime: Date,
    val playTime: Int,
    val thumbnail: String,
    val author: String,
    val isBookmarked: Boolean,
) : MediaInfo