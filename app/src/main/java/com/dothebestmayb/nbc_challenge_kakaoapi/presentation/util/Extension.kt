package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.util

import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.smoothSnapToPosition(position: Int) {
    val scrollDuration = 500f
    val smoothScroller = object: LinearSmoothScroller(this.context) {
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
            return scrollDuration / computeVerticalScrollRange()
        }
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}