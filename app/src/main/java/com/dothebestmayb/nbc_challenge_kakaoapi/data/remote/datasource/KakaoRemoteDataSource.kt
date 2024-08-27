package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.GetImageRequest
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchImageResponse

interface KakaoRemoteDataSource {

    suspend fun getImage(getImageRequest: GetImageRequest): SearchImageResponse
}