package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.bookmark

import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.bookmark.model.BookmarkItem

fun interface BookmarkOnClickListener {

    fun onBookmarkClick(item: BookmarkItem)
}