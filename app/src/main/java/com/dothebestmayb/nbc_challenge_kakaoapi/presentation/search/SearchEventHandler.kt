package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.BookmarkingEvent
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util.EventBus
import kotlinx.coroutines.launch

class SearchEventHandler {
    suspend fun postEvent(event: BookmarkingEvent) {
        EventBus.publishToSearch(event)
    }

    fun subscribeEvent(lifecycleOwner: LifecycleOwner, callback: (BookmarkingEvent) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            EventBus.subscribeSearch<BookmarkingEvent> {
                callback(it)
            }
        }
    }
}