package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource

import androidx.annotation.IntRange
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchImageResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.service.KakaoService

interface KakaoRemoteDataSource {

    suspend fun getImage(
        query: String,
        sort: SortType = SortType.ACCURACY,
        @IntRange(
            KakaoService.MIN_PAGE_INDEX,
            KakaoService.MAX_PAGE_INDEX
        ) page: Int = KakaoService.DEFAULT_PAGE_INDEX,
        @IntRange(
            KakaoService.MIN_DATA_SIZE,
            KakaoService.MAX_DATA_SIZE
        ) size: Int = KakaoService.DEFAULT_DATA_SIZE,
    ): SearchImageResponse
}