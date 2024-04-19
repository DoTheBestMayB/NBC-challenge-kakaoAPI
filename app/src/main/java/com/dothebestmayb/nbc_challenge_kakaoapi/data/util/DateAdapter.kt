package com.dothebestmayb.nbc_challenge_kakaoapi.data.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.Date

class DateAdapter {
    @ToJson
    fun toJson(datetime: Date): String {
        return DateUtil.formatDate(datetime)
    }

    @FromJson
    fun fromJson(datetime: String): Date {
        return DateUtil.parseDate(datetime)
    }
}