package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.GetImageRequest
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchImageResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.service.KakaoService
import javax.inject.Inject

internal class KakaoRemoteDataSourceImpl @Inject constructor(
    private val kakaoService: KakaoService
) : KakaoRemoteDataSource {
    override suspend fun getImage(getImageRequest: GetImageRequest): SearchImageResponse {
        return kakaoService.getImage(getImageRequest)
    }

}