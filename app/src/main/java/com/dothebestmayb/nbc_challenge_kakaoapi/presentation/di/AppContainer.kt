package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di

import com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource.KakaoRemoteDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource.KakaoRemoteDataSourceImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.data.network.KakaoService
import com.dothebestmayb.nbc_challenge_kakaoapi.data.network.RetrofitClient
import retrofit2.Retrofit

class AppContainer() {
    fun createRetrofit(): Retrofit {
        return RetrofitClient.retrofitClient
    }

    fun createKakaoService(): KakaoService {
        return createRetrofit().create(KakaoService::class.java)
    }

    fun createKakaoRemoteDataSource(): KakaoRemoteDataSource {
        return KakaoRemoteDataSourceImpl(createKakaoService())
    }

    var searchContainer: SearchContainer? = null
}
