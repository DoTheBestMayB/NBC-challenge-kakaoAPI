package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
sealed class MediaInfo : Parcelable {
    data class ImageInfo(
        val collection: String,
        val thumbnailUrl: String,
        val imageUrl: String,
        val width: Int,
        val height: Int,
        val displaySiteName: String,
        val docUrl: String,
        val datetime: Date,
    ) : MediaInfo()

    data class VideoInfo(
        val title: String,
        val url: String,
        val datetime: Date,
        val playTime: Int,
        val thumbnail: String,
        val author: String,
    ) : MediaInfo()
}