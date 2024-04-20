package com.dothebestmayb.nbc_challenge_kakaoapi.data.database

import androidx.room.TypeConverter
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.DateUtil
import java.util.Date

class Converters {
    @TypeConverter
    fun parseDateToString(date: Date): String {
        return DateUtil.formatDate(date)
    }

    @TypeConverter
    fun parseStringToDate(value: String): Date {
        return DateUtil.parseDate(value)
    }
}