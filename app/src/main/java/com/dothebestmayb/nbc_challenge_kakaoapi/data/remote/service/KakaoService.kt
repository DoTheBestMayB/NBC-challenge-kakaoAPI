package com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.service

import androidx.annotation.IntRange
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {

    @GET("search/image")
    suspend fun getImage(
        @Query("query") query: String,
        @Query("sort") sort: SortType = SortType.ACCURACY,
        @Query("page") @IntRange(
            IMAGE_MIN_PAGE_INDEX,
            IMAGE_MAX_PAGE_INDEX
        ) page: Int = IMAGE_DEFAULT_PAGE_INDEX,
        @Query("size") @IntRange(
            IMAGE_MIN_DATA_SIZE,
            IMAGE_MAX_DATA_SIZE
        ) size: Int = IMAGE_DEFAULT_DATA_SIZE,
    ): SearchResponse<SearchDocumentResponse.ImageDocument>

    @GET("search/video")
    suspend fun getVideo(
        @Query("query") query: String,
        @Query("sort") sort: SortType = SortType.ACCURACY,
        @Query("page") @IntRange(
            VIDEO_MIN_PAGE_INDEX,
            VIDEO_MAX_PAGE_INDEX
        ) page: Int,
        @Query("size") @IntRange(
            VIDEO_MIN_DATA_SIZE,
            VIDEO_MAX_DATA_SIZE
        ) size: Int = VIDEO_DEFAULT_DATA_SIZE,
    ): SearchResponse<SearchDocumentResponse.VideoDocument>

    companion object {
        const val IMAGE_MIN_PAGE_INDEX = 1L
        const val IMAGE_MAX_PAGE_INDEX = 50L
        const val IMAGE_DEFAULT_PAGE_INDEX = 1

        const val IMAGE_MIN_DATA_SIZE = 1L
        const val IMAGE_MAX_DATA_SIZE = 80L
        const val IMAGE_DEFAULT_DATA_SIZE = 80

        const val VIDEO_MIN_PAGE_INDEX = 1L
        const val VIDEO_MAX_PAGE_INDEX = 15L

        const val VIDEO_MIN_DATA_SIZE = 1L
        const val VIDEO_MAX_DATA_SIZE = 30L
        const val VIDEO_DEFAULT_DATA_SIZE = 15
    }
}