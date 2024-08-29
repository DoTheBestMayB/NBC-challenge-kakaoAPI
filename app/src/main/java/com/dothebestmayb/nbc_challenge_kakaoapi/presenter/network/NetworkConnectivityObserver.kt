package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 참고자료 : https://www.youtube.com/watch?v=TzV0oCRDNfM
 */
class NetworkConnectivityObserver @Inject constructor(
    @ApplicationContext private val context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<NetworkStatus> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {

                private val networks = mutableSetOf<Network>()

                override fun onAvailable(network: Network) {
                    networks += network

                    launch { send(NetworkStatus.AVAILABLE) }
                }

                override fun onLost(network: Network) {
                    networks -= network

                    launch {
                        val status =if (networks.isEmpty()) {
                            NetworkStatus.LOST
                        } else {
                            NetworkStatus.AVAILABLE
                        }
                        send(status)
                    }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}