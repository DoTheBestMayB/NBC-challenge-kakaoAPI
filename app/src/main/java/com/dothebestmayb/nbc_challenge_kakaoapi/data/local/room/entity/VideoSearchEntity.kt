package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo
import java.time.LocalDateTime

@Entity
data class VideoSearchEntity(
    @PrimaryKey @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "search_keyword") val searchKeyword: String,
    @ColumnInfo(name = "title") val title: String,
    val datetime: LocalDateTime,
    @ColumnInfo(name = "play_time") val playTime: Int,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
) {
    fun toDomain(): SearchInfo.VideoSearchInfo = SearchInfo.VideoSearchInfo(
        title = title,
        url = url,
        datetime = datetime,
        playTime = playTime,
        thumbnail = thumbnail,
    )
}
