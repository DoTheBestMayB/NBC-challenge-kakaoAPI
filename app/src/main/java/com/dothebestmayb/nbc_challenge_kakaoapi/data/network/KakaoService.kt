package com.dothebestmayb.nbc_challenge_kakaoapi.data.network

import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.ImageDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.SearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.VideoDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {

    @GET("v2/search/image")
    suspend fun getImage(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): NetworkResult<SearchResponse<ImageDocumentResponse>>

    @GET("v2/search/vclip")
    suspend fun getVideo(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): NetworkResult<SearchResponse<VideoDocumentResponse>>
}