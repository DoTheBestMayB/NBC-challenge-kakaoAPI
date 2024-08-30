package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.di

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource.BookmarkLocalDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource.BookmarkLocalDataSourceImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource.KakaoLocalDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource.KakaoLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsKakaoDataSource(kakaoLocalDataSourceImpl: KakaoLocalDataSourceImpl): KakaoLocalDataSource

    @Binds
    @Singleton
    abstract fun bindsBookmarkDataSource(bookmarkLocalDataSourceImpl: BookmarkLocalDataSourceImpl): BookmarkLocalDataSource
}