package com.dothebestmayb.nbc_challenge_kakaoapi.domain.model

import java.util.Date

data class VideoDocumentEntity(
    val title: String,
    val url: String,
    override val dateTime: Date,
    val playTime: Int,
    val thumbnail: String,
    val author: String,
) : DocumentEntity
