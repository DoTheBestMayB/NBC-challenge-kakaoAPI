package com.dothebestmayb.nbc_challenge_kakaoapi.data.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {

    private val parseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.KOREA)
    private val formatFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)

    fun parseDate(dateString: String): Date {
        return parseFormat.parse(dateString)
    }

    fun formatDate(date: Date): String {
        return formatFormat.format(date)
    }
}