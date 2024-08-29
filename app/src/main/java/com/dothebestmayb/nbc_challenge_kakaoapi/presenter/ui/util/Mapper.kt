package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.util

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model.SearchItem

fun SearchInfo.ImageSearchInfo.toUi() = SearchItem.Image(
    thumbnail = thumbnailUrl,
    datetime = datetime,
    docUrl = docUrl,
    isBookmarked = false
)

fun SearchInfo.VideoSearchInfo.toUi() = SearchItem.Video(
    datetime = datetime,
    title = title,
    url = url,
    playTime = playTime,
    thumbnail = thumbnail,
    isBookmarked = false,
)