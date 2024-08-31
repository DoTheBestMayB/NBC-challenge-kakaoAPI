package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model

import java.time.LocalDateTime

data class SearchHistory(
    val query: String,
    val datetime: LocalDateTime,
)
