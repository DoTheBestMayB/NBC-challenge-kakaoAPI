package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model

import java.time.LocalDateTime

data class SearchItem(
    val thumbnail: String,
    val datetime: LocalDateTime,
    val type: SearchType,
    val isBookmarked: Boolean,
)
