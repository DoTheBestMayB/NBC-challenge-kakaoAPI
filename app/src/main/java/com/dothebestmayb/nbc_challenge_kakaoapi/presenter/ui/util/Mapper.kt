package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.util

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageSearchInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.SearchItem
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.SearchType

fun ImageSearchInfo.toUi() = SearchItem(
    thumbnail = thumbnailUrl,
    datetime = datetime,
    type = SearchType.IMAGE,
    isBookmarked = false
)