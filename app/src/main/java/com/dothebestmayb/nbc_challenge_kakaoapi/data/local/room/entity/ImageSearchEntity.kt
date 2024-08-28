package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class ImageSearchEntity(
    @PrimaryKey @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "search_keyword") val searchKeyword: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
    @ColumnInfo(name = "display_site_name") val displaySiteName: String,
    @ColumnInfo(name = "doc_url") val docUrl: String,
    @ColumnInfo(name = "datetime") val datetime: LocalDateTime,
)
