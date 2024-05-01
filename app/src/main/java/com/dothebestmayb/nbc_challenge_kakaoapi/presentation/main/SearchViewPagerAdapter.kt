package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dothebestmayb.nbc_challenge_kakaoapi.R
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark.BookmarkFragment
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search.SearchFragment

class SearchViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private val fragments = listOf(
        SearchTabModel(SearchFragment(), R.string.search, R.drawable.baseline_search_24),
        SearchTabModel(BookmarkFragment(), R.string.bookmark, R.drawable.baseline_bookmark_24),
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position].fragment

    fun getTitle(position: Int) = fragments[position].title

    fun getIcon(position: Int) = fragments[position].icon
}