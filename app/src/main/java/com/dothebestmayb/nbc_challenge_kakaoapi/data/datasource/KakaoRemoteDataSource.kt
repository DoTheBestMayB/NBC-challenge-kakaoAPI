package com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.ImageDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.SearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.VideoDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult

interface KakaoRemoteDataSource {

    suspend fun getImage(
        query: String,
        page: Int,
        size: Int,
    ): NetworkResult<SearchResponse<ImageDocumentResponse>>

    suspend fun getVideo(
        query: String,
        page: Int,
        size: Int
    ): NetworkResult<SearchResponse<VideoDocumentResponse>>
}