package com.dothebestmayb.nbc_challenge_kakaoapi.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark.BookmarkFragment
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.TabType
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search.SearchFragment

class SearchCollectionAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = TabType.entries.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TabType.SEARCH.position -> SearchFragment()
            TabType.BOOKMARK.position -> BookmarkFragment()
            else -> throw IllegalArgumentException("Not implemented yet")
        }
    }
}