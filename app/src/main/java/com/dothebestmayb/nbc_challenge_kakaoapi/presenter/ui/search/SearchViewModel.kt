package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {


    fun onBookmarkClick(item: SearchItem) {
        viewModelScope.launch {

        }
    }
}