package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.util

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateConverter {

    private val dateFormat = DateTimeFormatter.ofPattern(DATETIME_FORMAT)

    @TypeConverter
    fun fromStatement(statement: String): LocalDateTime {
        return LocalDateTime.parse(statement, dateFormat)
    }

    @TypeConverter
    fun dateToStatement(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }

    companion object {
        const val DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    }
}