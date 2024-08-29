package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model

import java.time.LocalDateTime

sealed interface SearchItem {

    val datetime: LocalDateTime

    data class Image(
        override val datetime: LocalDateTime,
        val thumbnail: String,
        val docUrl: String,
        val isBookmarked: Boolean,
    ) : SearchItem

    data class Video(
        override val datetime: LocalDateTime,
        val title: String,
        val url: String,
        val playTime: Int,
        val thumbnail: String,
        val isBookmarked: Boolean,
    ) : SearchItem
}
