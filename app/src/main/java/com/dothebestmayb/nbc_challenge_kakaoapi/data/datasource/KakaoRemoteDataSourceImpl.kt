package com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.ImageDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.SearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.VideoDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.network.KakaoService
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult

class KakaoRemoteDataSourceImpl(
    private val kakaoService: KakaoService
) : KakaoRemoteDataSource {
    override suspend fun getImage(
        query: String,
        page: Int,
        size: Int
    ): NetworkResult<SearchResponse<ImageDocumentResponse>> {
        return kakaoService.getImage(query, page, size)
    }

    override suspend fun getVideo(
        query: String,
        page: Int,
        size: Int
    ): NetworkResult<SearchResponse<VideoDocumentResponse>> {
        return kakaoService.getVideo(query, page, size)
    }
}