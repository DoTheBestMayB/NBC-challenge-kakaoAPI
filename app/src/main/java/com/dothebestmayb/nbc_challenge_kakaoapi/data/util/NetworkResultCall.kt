package com.dothebestmayb.nbc_challenge_kakaoapi.data.util

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResultCall<T : Any>(
    private val proxy: Call<T>
) : Call<NetworkResult<T>> {
    override fun clone(): Call<NetworkResult<T>> {
        return NetworkResultCall(proxy.clone())
    }

    override fun execute(): Response<NetworkResult<T>> {
        throw NotImplementedError()
    }

    override fun isExecuted(): Boolean {
        return proxy.isExecuted
    }

    override fun cancel() {
        proxy.cancel()
    }

    override fun isCanceled(): Boolean {
        return proxy.isCanceled
    }

    override fun request(): Request {
        return proxy.request()
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }

    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult = handleApi { response }
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = NetworkResult.Exception<T>(t)
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }

        })
    }


}