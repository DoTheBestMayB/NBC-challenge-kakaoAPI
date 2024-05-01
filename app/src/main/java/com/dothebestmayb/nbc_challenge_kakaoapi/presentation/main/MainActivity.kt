package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter: SearchViewPagerAdapter by lazy {
        SearchViewPagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setViewPager()
        setTabLayout()
    }

    private fun setViewPager() = with(binding) {
        pager.adapter = adapter
        pager.isUserInputEnabled = false
        pager.offscreenPageLimit = 1
    }

    private fun setTabLayout() = with(binding) {
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.setIcon(adapter.getIcon(position))
            tab.setText(adapter.getTitle(position))
        }.attach()
    }
}