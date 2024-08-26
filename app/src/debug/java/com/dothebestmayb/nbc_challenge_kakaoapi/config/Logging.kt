package com.dothebestmayb.nbc_challenge_kakaoapi.config

import okhttp3.logging.HttpLoggingInterceptor

object Logging {
    val HTTP_DEBUG_LEVEL = HttpLoggingInterceptor.Level.BODY
}