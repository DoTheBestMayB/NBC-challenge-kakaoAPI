package com.dothebestmayb.nbc_challenge_kakaoapi.domain.model

import java.time.LocalDateTime

data class SearchHistoryInfo(
    val query: String,
    val datetime: LocalDateTime,
)
