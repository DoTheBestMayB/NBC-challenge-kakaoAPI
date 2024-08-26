package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.service

import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.GetImageRequest
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchImageResponse
import retrofit2.http.Body
import retrofit2.http.GET

interface KakaoService {

    @GET("/search/image")
    suspend fun getImage(
        @Body getImageRequest: GetImageRequest,
    ): SearchImageResponse
}