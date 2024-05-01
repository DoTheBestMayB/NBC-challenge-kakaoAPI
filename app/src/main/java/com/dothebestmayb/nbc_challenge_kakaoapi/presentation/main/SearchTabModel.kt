package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

data class SearchTabModel (
    val fragment: Fragment,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
)