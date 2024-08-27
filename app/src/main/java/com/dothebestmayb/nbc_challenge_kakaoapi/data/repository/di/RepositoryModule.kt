package com.dothebestmayb.nbc_challenge_kakaoapi.data.repository.di

import com.dothebestmayb.nbc_challenge_kakaoapi.data.repository.KakaoSearchRepositoryImpl
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsKakaoRepository(kakaoSearchRepositoryImpl: KakaoSearchRepositoryImpl): KakaoSearchRepository
}