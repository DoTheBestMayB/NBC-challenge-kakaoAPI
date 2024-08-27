package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.di

import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource.KakaoRemoteDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource.KakaoRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsKakaoDataSource(kakaoRemoteDataSourceImpl: KakaoRemoteDataSourceImpl): KakaoRemoteDataSource
}