package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<NetworkStatus>
}