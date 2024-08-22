package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

data class SearchItem(
    val thumbnail: String,
    val datetime: String,
    val type: SearchType,
    val isBookmarked: Boolean,
)
