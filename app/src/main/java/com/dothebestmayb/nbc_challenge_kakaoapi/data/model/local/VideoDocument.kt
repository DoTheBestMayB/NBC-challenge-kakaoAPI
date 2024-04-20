package com.dothebestmayb.nbc_challenge_kakaoapi.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "videoDocuments")
data class VideoDocument(
    val title: String,
    @PrimaryKey val url: String,
    val datetime: Date,
    @ColumnInfo(name = "play_time") val playTime: Int,
    val thumbnail: String,
    val author: String,
)
