package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model.SearchItem

fun interface BookmarkOnClickListener {

    fun onClick(item: SearchItem)
}