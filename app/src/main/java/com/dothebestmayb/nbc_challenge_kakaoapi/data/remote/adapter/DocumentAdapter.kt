package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.adapter

import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.Document
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DocumentAdapter: JsonAdapter<Document>() {

    private val dateFormat = DateTimeFormatter.ofPattern(DATETIME_FORMAT)

    @FromJson
    override fun fromJson(reader: JsonReader): Document? {
        return try {
            Document(
                collection = reader.nextString(),
                thumbnailUrl = reader.nextString(),
                imageUrl = reader.nextString(),
                width = reader.nextInt(),
                height = reader.nextInt(),
                displaySiteName = reader.nextString(),
                docUrl = reader.nextString(),
                datetime = LocalDateTime.parse(reader.nextString(), dateFormat),
            )
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, document: Document?) {
        if (document != null) {
            writer.value(document.toString())
        }
    }

    companion object {
        const val DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    }
}