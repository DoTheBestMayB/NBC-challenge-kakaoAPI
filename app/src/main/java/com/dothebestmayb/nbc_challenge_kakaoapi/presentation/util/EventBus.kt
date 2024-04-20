package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.coroutines.coroutineContext

object EventBus {
    private val _bookMarkEvents = MutableSharedFlow<Any>()
    val bookMarkEvents = _bookMarkEvents.asSharedFlow()

    private val _searchMarkEvents = MutableSharedFlow<Any>()
    val searchMarkEvents = _searchMarkEvents.asSharedFlow()

    suspend fun publishToBookmark(event: Any) {
        _bookMarkEvents.emit(event)
    }

    suspend inline fun <reified T> subscribeBookmark(crossinline onEvent: (T) -> Unit) {
        bookMarkEvents.filterIsInstance<T>()
            .collectLatest { event ->
                coroutineContext.ensureActive()
                onEvent(event)
            }
    }

    suspend fun publishToSearch(event: Any) {
        _searchMarkEvents.emit(event)
    }

    suspend inline fun <reified T> subscribeSearch(crossinline onEvent: (T) -> Unit) {
        searchMarkEvents.filterIsInstance<T>()
            .collectLatest { event ->
                coroutineContext.ensureActive()
                onEvent(event)
            }
    }
}