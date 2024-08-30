package com.dothebestmayb.nbc_challenge_kakaoapi.data.util

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo

fun SearchInfo.toEntity(): SearchEntity {
    return when (this) {
        is SearchInfo.ImageSearchInfo -> SearchEntity(
            url = imageUrl,
            searchKeyword = searchKeyword,
            thumbnailUrl = thumbnailUrl,
            datetime = datetime,
            type = SearchEntity.SearchType.IMAGE,
            displaySiteName = displaySiteName,
            docUrl = docUrl,
            bookmarked = bookmarked,
        )

        is SearchInfo.VideoSearchInfo -> SearchEntity(
            url = url,
            searchKeyword = searchKeyword,
            thumbnailUrl = thumbnail,
            datetime = datetime,
            type = SearchEntity.SearchType.VIDEO,
            title = title,
            playTime = playTime,
            bookmarked = bookmarked,
        )
    }
}