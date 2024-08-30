package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.util

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model.SearchItem

fun SearchInfo.ImageSearchInfo.toUi() = SearchItem.Image(
    thumbnail = thumbnailUrl,
    imageUrl = imageUrl,
    displaySiteName = displaySiteName,
    datetime = datetime,
    docUrl = docUrl,
    bookmarked = bookmarked,
    searchKeyword = searchKeyword,
)

fun SearchInfo.VideoSearchInfo.toUi() = SearchItem.Video(
    datetime = datetime,
    title = title,
    url = url,
    playTime = playTime,
    thumbnail = thumbnail,
    isBookmarked = bookmarked,
    searchKeyword = searchKeyword,
)