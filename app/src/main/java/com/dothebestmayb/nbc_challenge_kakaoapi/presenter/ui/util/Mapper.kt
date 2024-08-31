package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.util

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.BookmarkInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchHistoryInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.bookmark.model.BookmarkItem
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model.SearchHistory
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model.SearchItem

fun SearchInfo.Image.toUi() = SearchItem.Image(
    thumbnail = thumbnailUrl,
    imageUrl = imageUrl,
    displaySiteName = displaySiteName,
    datetime = datetime,
    docUrl = docUrl,
    bookmarked = bookmarked,
    searchKeyword = searchKeyword,
)

fun SearchInfo.Video.toUi() = SearchItem.Video(
    datetime = datetime,
    title = title,
    url = url,
    playTime = playTime,
    thumbnail = thumbnail,
    bookmarked = bookmarked,
    searchKeyword = searchKeyword,
)

fun BookmarkInfo.Image.toUi() = BookmarkItem.Image(
    datetime = datetime,
    thumbnail = thumbnailUrl,
    imageUrl = imageUrl,
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    bookmarked = bookmarked,
    searchKeyword = searchKeyword,
)

fun BookmarkInfo.Video.toUi() = BookmarkItem.Video(
    datetime = datetime,
    title = title,
    url = url,
    playTime = playTime,
    thumbnail = thumbnail,
    bookmarked = bookmarked,
    searchKeyword = searchKeyword
)

fun SearchHistoryInfo.toUi() = SearchHistory(
    query = query,
    datetime = datetime,
)