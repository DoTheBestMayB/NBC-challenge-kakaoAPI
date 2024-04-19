package com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.ImageSearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.network.KakaoService
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult

class KakaoRemoteDataSourceImpl(
    private val kakaoService: KakaoService
) : KakaoRemoteDataSource {
    override suspend fun getImage(
        query: String,
        page: Int,
        size: Int
    ): NetworkResult<ImageSearchResponse> {
        return kakaoService.getImage(query, page, size)
    }
}