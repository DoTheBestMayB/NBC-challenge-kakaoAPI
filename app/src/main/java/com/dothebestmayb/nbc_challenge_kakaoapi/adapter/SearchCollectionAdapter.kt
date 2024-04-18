package com.dothebestmayb.nbc_challenge_kakaoapi.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.SearchObjectFragment
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.TabType

class SearchCollectionAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = TabType.entries.size

    override fun createFragment(position: Int): Fragment {
        val fragment = SearchObjectFragment().apply {
            arguments = Bundle().apply {
                putParcelable(SearchObjectFragment.ARG_TYPE, TabType.from(position))
            }
        }
        return fragment
    }

}