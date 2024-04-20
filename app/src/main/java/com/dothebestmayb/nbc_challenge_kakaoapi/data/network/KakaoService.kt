package com.dothebestmayb.nbc_challenge_kakaoapi.data.network

import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.ImageSearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.VideoSearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {

    @GET("v2/search/image")
    suspend fun getImage(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): NetworkResult<ImageSearchResponse>

    @GET("v2/search/vclip")
    suspend fun getVideo(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): NetworkResult<VideoSearchResponse>
}