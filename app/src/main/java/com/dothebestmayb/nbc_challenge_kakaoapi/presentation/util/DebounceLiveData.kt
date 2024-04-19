package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

// 출처 : https://gist.github.com/luciofm/3ae1c0869cf9a05cd9a2e9e5baa9c1c9
class DebounceLiveData<T>(
    private val source: LiveData<T>,
    private val debounceMs: Long,
) : LiveData<T>(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var debounceJob: Job? = null

    private val observer = Observer<T> { source ->
        debounceJob?.cancel()
        debounceJob = launch {
            delay(debounceMs)
            value = source
        }
    }

    override fun onActive() {
        source.observeForever(observer)
    }

    override fun onInactive() {
        debounceJob?.cancel()
        source.removeObserver(observer)
    }
}

fun <T> LiveData<T>.debounce(
    time: Long,
    unit: TimeUnit = TimeUnit.MILLISECONDS,
): LiveData<T> {
    val timeMs = when (unit) {
        TimeUnit.NANOSECONDS -> unit.toMillis(time)
        TimeUnit.MICROSECONDS -> unit.toMillis(time)
        TimeUnit.MILLISECONDS -> time
        TimeUnit.SECONDS -> unit.toMillis(time)
        TimeUnit.MINUTES -> unit.toMillis(time)
        TimeUnit.HOURS -> unit.toMillis(time)
        TimeUnit.DAYS -> unit.toMillis(time)
    }

    return DebounceLiveData(this, timeMs)
}