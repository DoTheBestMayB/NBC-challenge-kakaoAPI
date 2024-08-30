package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo
import java.time.LocalDateTime

sealed interface SearchItem {

    val datetime: LocalDateTime
    val bookmarked: Boolean

    fun switchBookmarkState(): SearchItem
    fun toDomain(): SearchInfo

    data class Image(
        override val datetime: LocalDateTime,
        override val bookmarked: Boolean,
        val thumbnail: String,
        val imageUrl: String,
        val displaySiteName: String,
        val docUrl: String,
        val searchKeyword: String,
    ) : SearchItem {
        override fun switchBookmarkState(): SearchItem {
            return copy(bookmarked = bookmarked.not())
        }

        override fun toDomain(): SearchInfo {
            return SearchInfo.Image(
                thumbnailUrl = thumbnail,
                imageUrl = imageUrl,
                displaySiteName = displaySiteName,
                docUrl = docUrl,
                datetime = datetime,
                searchKeyword = searchKeyword,
                bookmarked = bookmarked,
            )
        }
    }

    data class Video(
        override val datetime: LocalDateTime,
        override val bookmarked: Boolean,
        val title: String,
        val url: String,
        val playTime: Int,
        val thumbnail: String,
        val searchKeyword: String,
    ) : SearchItem {
        override fun switchBookmarkState(): SearchItem {
            return copy(bookmarked = bookmarked.not())
        }

        override fun toDomain(): SearchInfo {
            return SearchInfo.Video(
                title = title,
                url = url,
                datetime = datetime,
                playTime = playTime,
                thumbnail = thumbnail,
                searchKeyword = searchKeyword,
                bookmarked = bookmarked,
            )
        }
    }
}
