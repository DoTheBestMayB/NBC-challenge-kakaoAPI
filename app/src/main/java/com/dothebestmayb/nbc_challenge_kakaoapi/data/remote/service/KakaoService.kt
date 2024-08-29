package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.service

import androidx.annotation.IntRange
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {

    @GET("search/image")
    suspend fun getImage(
        @Query("query") query: String,
        @Query("sort") sort: SortType = SortType.ACCURACY,
        @Query("page") @IntRange(
            MIN_PAGE_INDEX,
            MAX_PAGE_INDEX
        ) page: Int = DEFAULT_PAGE_INDEX,
        @Query("size") @IntRange(MIN_DATA_SIZE, MAX_DATA_SIZE) size: Int = DEFAULT_DATA_SIZE,
    ): SearchImageResponse

    companion object {
        const val MIN_PAGE_INDEX = 1L
        const val MAX_PAGE_INDEX = 50L
        const val DEFAULT_PAGE_INDEX = 1

        const val MIN_DATA_SIZE = 1L
        const val MAX_DATA_SIZE = 80L
        const val DEFAULT_DATA_SIZE = 80
    }
}