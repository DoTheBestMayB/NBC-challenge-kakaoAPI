package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search

import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo

sealed interface SearchEvent {

    data class UpdateBookmark(
        val mediaInfo: MediaInfo,
        val bookmarked: Boolean,
    ) : SearchEvent
}