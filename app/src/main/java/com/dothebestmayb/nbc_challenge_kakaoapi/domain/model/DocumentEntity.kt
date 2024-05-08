package com.dothebestmayb.nbc_challenge_kakaoapi.domain.model

import java.util.Date

sealed interface DocumentEntity {
    val dateTime: Date
}