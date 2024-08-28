package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.util

import androidx.room.TypeConverter
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.adapter.DocumentAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateConverter {

    private val dateFormat = DateTimeFormatter.ofPattern(DocumentAdapter.DATETIME_FORMAT)

    @TypeConverter
    fun fromStatement(statement: String): LocalDateTime {
        return LocalDateTime.parse(statement)
    }

    @TypeConverter
    fun dateToStatement(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }

    companion object {
        const val DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    }
}