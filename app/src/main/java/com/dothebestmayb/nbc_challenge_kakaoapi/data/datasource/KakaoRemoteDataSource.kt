package com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource

import androidx.paging.PagingData
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.SearchDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.VideoDocumentResponse
import kotlinx.coroutines.flow.Flow

interface KakaoRemoteDataSource {

    fun getImage(
        query: String,
        size: Int,
    ): Flow<PagingData<SearchDocumentResponse>>

    fun getVideo(
        query: String,
        size: Int
    ): Flow<PagingData<VideoDocumentResponse>>
}