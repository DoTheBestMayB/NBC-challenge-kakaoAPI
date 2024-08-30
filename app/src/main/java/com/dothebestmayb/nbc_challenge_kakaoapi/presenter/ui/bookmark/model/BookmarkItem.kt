package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.bookmark.model

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.BookmarkInfo
import java.time.LocalDateTime

sealed interface BookmarkItem {

    val datetime: LocalDateTime

    fun switchBookmarkState(): BookmarkItem
    fun toDomain(): BookmarkInfo

    data class Image(
        override val datetime: LocalDateTime,
        val thumbnail: String,
        val imageUrl: String,
        val displaySiteName: String,
        val docUrl: String,
        val bookmarked: Boolean,
        val searchKeyword: String,
    ) : BookmarkItem {
        override fun switchBookmarkState(): BookmarkItem {
            return copy(bookmarked = bookmarked.not())
        }

        override fun toDomain(): BookmarkInfo {
            return BookmarkInfo.Image(
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
        val title: String,
        val url: String,
        val playTime: Int,
        val thumbnail: String,
        val bookmarked: Boolean,
        val searchKeyword: String,
    ) : BookmarkItem {
        override fun switchBookmarkState(): BookmarkItem {
            return copy(bookmarked = bookmarked.not())
        }

        override fun toDomain(): BookmarkInfo {
            return BookmarkInfo.Video(
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