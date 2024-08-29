package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.di

import com.dothebestmayb.nbc_challenge_kakaoapi.BuildConfig
import com.dothebestmayb.nbc_challenge_kakaoapi.config.Logging
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.adapter.LocalDateTimeAdapter
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.service.KakaoService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val KAKAO_BASE_URL = "https://dapi.kakao.com/v2/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshiConverter(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = Logging.HTTP_DEBUG_LEVEL
        }
    }

    @Provides
    @Singleton
    fun providesKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val headers = request.headers.newBuilder().add("Authorization", "KakaoAK ${BuildConfig.KAKAO_API_KEY}").build()
            return@Interceptor chain.proceed(request.newBuilder().headers(headers).build())
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        keyInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(keyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideKakaoRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(KAKAO_BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideKakaoApiService(
        retrofit: Retrofit
    ): KakaoService {
        return retrofit.create(KakaoService::class.java)
    }
}