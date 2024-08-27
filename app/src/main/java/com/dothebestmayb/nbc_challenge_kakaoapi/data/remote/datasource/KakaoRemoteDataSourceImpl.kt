package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.GetImageRequest
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchImageResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.service.KakaoService

internal class KakaoRemoteDataSourceImpl(
    private val kakaoService: KakaoService
) : KakaoRemoteDataSource {
    override suspend fun getImage(getImageRequest: GetImageRequest): SearchImageResponse {
        return kakaoService.getImage(getImageRequest)
    }

}