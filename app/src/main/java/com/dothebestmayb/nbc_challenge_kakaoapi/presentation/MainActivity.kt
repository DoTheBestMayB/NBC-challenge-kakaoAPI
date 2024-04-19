package com.dothebestmayb.nbc_challenge_kakaoapi.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.ActivityMainBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.TabType
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter: SearchCollectionAdapter by lazy {
        SearchCollectionAdapter(this)
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
            val tabType = TabType.from(position)
            tab.icon = AppCompatResources.getDrawable(baseContext, TabType.from(position).iconRes)
            tab.text = getString(tabType.textName)
        }.attach()
    }
}