package com.dothebestmayb.nbc_challenge_kakaoapi.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "imageDocuments")
data class ImageDocument(
    val collection: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String,
    @PrimaryKey @ColumnInfo(name = "image_url") val imageUrl: String,
    val width: Int,
    val height: Int,
    @ColumnInfo(name = "display_site_name") val displaySiteName: String,
    @ColumnInfo(name = "doc_url") val docUrl: String,
    val datetime: Date,
)
