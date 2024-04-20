package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.BookmarkingEvent
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.EventBus
import kotlinx.coroutines.launch

class BookmarkEventHandler {
    suspend fun postEvent(event: BookmarkingEvent) {
        EventBus.publishToBookmark(event)
    }

    fun subscribeEvent(lifecycleOwner: LifecycleOwner, callback: (BookmarkingEvent) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            EventBus.subscribeBookmark<BookmarkingEvent> {
                callback(it)
            }
        }
    }
}