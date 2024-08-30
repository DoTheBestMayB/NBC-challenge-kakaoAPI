package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class SearchEntity(
    @PrimaryKey val url: String,

    // 공통
    @ColumnInfo(name = "search_keyword") val searchKeyword: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
    val datetime: LocalDateTime,
    @ColumnInfo val type: SearchType,
    val bookmarked: Boolean,

    // 이미지
    @ColumnInfo(name = "display_site_name") val displaySiteName: String? = null,
    @ColumnInfo(name = "doc_url") val docUrl: String? = null,

    // 비디오
    val title: String? = null,
    @ColumnInfo(name = "play_time") val playTime: Int? = null,
) {
    enum class SearchType {
        IMAGE, VIDEO
    }
}