package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark

import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo

sealed interface BookmarkEvent {

    data class UpdateBookmark(
        val mediaInfo: MediaInfo,
        val bookmarked: Boolean,
    ) : BookmarkEvent
}