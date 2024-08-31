package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

interface SearchHistoryOnClickListener {

    fun onClick(query: String)

    fun onDelete(query: String)
}