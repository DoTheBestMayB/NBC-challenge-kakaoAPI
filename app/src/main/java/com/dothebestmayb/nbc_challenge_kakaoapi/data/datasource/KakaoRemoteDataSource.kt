package com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.ImageSearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.VideoSearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult

interface KakaoRemoteDataSource {

    suspend fun getImage(
        query: String,
        page: Int,
        size: Int,
    ): NetworkResult<ImageSearchResponse>

    suspend fun getVideo(
        query: String,
        page: Int,
        size: Int
    ): NetworkResult<VideoSearchResponse>
}