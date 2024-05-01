package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model

enum class AdapterType(val viewTypeValue: Int) {
    IMAGE(0), VIDEO(1), Unknown(-1);

    companion object {
        fun from(viewTypeValue: Int) = entries.firstOrNull { it.viewTypeValue == viewTypeValue } ?: Unknown
    }
}