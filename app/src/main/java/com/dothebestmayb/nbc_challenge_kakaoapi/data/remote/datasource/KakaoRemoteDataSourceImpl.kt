package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchImageResponse
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
    ): SearchImageResponse {
        return kakaoService.getImage(query, sort, page, size)
    }
}