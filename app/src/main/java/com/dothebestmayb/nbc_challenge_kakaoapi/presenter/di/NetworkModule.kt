package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.di

import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.network.ConnectivityObserver
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.network.NetworkConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    internal abstract fun bindsNetworkConnectivityObserver(
        networkMonitor: NetworkConnectivityObserver,
    ): ConnectivityObserver
}