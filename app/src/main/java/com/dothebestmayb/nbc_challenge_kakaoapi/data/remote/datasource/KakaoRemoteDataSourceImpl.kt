package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.service.KakaoService
import javax.inject.Inject

internal class KakaoRemoteDataSourceImpl @Inject constructor(
    private val kakaoService: KakaoService
) : KakaoRemoteDataSource {
    override suspend fun getImage(
        query: String,
        sort: SortType,
        page: Int,
        size: Int
    ): SearchResponse<SearchDocumentResponse.ImageDocument> {
        return kakaoService.getImage(query, sort, page, size)
    }

    override suspend fun getVideo(
        query: String,
        sort: SortType,
        page: Int,
        size: Int
    ): SearchResponse<SearchDocumentResponse.VideoDocument> {
        return kakaoService.getVideo(query, sort, page, size)
    }
}