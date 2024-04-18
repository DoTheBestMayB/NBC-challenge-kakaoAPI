package com.dothebestmayb.nbc_challenge_kakaoapi.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo

class SearchResultViewModel: ViewModel() {

    private val _images = MutableLiveData<MediaInfo.ImageInfo>()
    val images: LiveData<MediaInfo.ImageInfo>
        get() = _images

    private val _videos = MutableLiveData<MediaInfo.VideoInfo>()
    val videos: LiveData<MediaInfo.VideoInfo>
        get() = _videos


    fun fetchFromServer() {

    }

    fun fetchFromLocal() {

    }
}