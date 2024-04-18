package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.dothebestmayb.nbc_challenge_kakaoapi.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class TabType(val position: Int, @DrawableRes val iconRes: Int) : Parcelable {
    SEARCH(0, R.drawable.baseline_search_24),
    BOOKMARK(1, R.drawable.baseline_bookmark_24);

    companion object {
        fun from(position: Int): TabType = entries.first { it.position == position }
    }
}