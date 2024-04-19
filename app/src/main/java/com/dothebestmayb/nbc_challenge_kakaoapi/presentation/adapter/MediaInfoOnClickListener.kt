package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.adapter

import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo

interface MediaInfoOnClickListener {

    fun onBookmarkChanged(mediaInfo: MediaInfo, isBookmarked: Boolean)
    fun remove(mediaInfo: MediaInfo)
}