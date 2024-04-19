package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dothebestmayb.nbc_challenge_kakaoapi.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class TabType(val position: Int, @DrawableRes val iconRes: Int, @StringRes val textName: Int) :
    Parcelable {
    SEARCH(0, R.drawable.baseline_search_24, R.string.search),
    BOOKMARK(1, R.drawable.baseline_bookmark_24, R.string.bookmark);

    companion object {
        fun from(position: Int): TabType = entries.first { it.position == position }
    }
}