package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.adapter

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter: JsonAdapter<LocalDateTime>() {

    private val dateFormat = DateTimeFormatter.ofPattern(DATETIME_FORMAT)

    @FromJson
    override fun fromJson(reader: JsonReader): LocalDateTime? {
        return try {
            LocalDateTime.parse(reader.nextString(), dateFormat)
        } catch (e: Exception) {
            Log.e("Exception", e.stackTraceToString())
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        if (value != null) {
            writer.value(value.format(dateFormat))
        }
    }

    companion object {
        const val DATETIME_FORMAT = "yyyy-MM-dd'T'HH[:mm][:ss][.SSS][XXX]"
    }
}