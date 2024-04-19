package com.dothebestmayb.nbc_challenge_kakaoapi.domain.model

import java.util.Date

data class VideoDocumentsEntity(
    val title: String,
    val url: String,
    val datetime: Date,
    val playTime: Int,
    val thumbnail: String,
    val author: String,
) : MediaInfo
