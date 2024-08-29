package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource

import androidx.annotation.IntRange
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.service.KakaoService
import retrofit2.http.Query

interface KakaoRemoteDataSource {

    suspend fun getImage(
        query: String,
        sort: SortType = SortType.ACCURACY,
        @IntRange(
            KakaoService.IMAGE_MIN_PAGE_INDEX,
            KakaoService.IMAGE_MAX_PAGE_INDEX
        ) page: Int = KakaoService.IMAGE_DEFAULT_PAGE_INDEX,
        @IntRange(
            KakaoService.IMAGE_MIN_DATA_SIZE,
            KakaoService.IMAGE_MAX_DATA_SIZE
        ) size: Int = KakaoService.IMAGE_DEFAULT_DATA_SIZE,
    ): SearchResponse<SearchDocumentResponse.ImageDocument>

    suspend fun getVideo(
        @Query("query") query: String,
        @Query("sort") sort: SortType = SortType.ACCURACY,
        @Query("page") @IntRange(
            KakaoService.VIDEO_MIN_PAGE_INDEX,
            KakaoService.VIDEO_MAX_PAGE_INDEX
        ) page: Int,
        @Query("size") @IntRange(
            KakaoService.VIDEO_MIN_DATA_SIZE,
            KakaoService.VIDEO_MAX_DATA_SIZE
        ) size: Int = KakaoService.VIDEO_DEFAULT_DATA_SIZE,
    ): SearchResponse<SearchDocumentResponse.VideoDocument>
}