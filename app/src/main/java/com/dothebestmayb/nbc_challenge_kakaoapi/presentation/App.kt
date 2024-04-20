package com.dothebestmayb.nbc_challenge_kakaoapi.presentation

import android.app.Application
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di.AppContainer

class App : Application() {

    val appContainer = AppContainer(this)
}