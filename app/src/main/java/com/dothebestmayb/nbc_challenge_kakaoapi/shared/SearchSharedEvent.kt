package com.dothebestmayb.nbc_challenge_kakaoapi.shared

import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo

sealed interface SearchSharedEvent{

    data class UpdateBookmark(
        val mediaInfo: MediaInfo,
        val bookmarked: Boolean,
    ): SearchSharedEvent
}