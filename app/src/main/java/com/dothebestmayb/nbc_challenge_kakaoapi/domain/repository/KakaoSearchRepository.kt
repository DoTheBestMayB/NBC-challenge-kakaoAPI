package com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoSearchEntity

interface KakaoSearchRepository {

    suspend fun getImage(
        query: String,
        page: Int,
        size: Int,
    ): NetworkResult<ImageSearchEntity>

    suspend fun getVideo(
        query: String,
        page: Int,
        size: Int,
    ): NetworkResult<VideoSearchEntity>
}