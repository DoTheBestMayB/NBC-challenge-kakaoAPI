package com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository

import androidx.annotation.IntRange
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo
import kotlinx.coroutines.flow.Flow

interface KakaoSearchRepository {

    suspend fun getItems(query: String): Flow<List<SearchInfo>>

    suspend fun fetchImage(
        query: String,
        @IntRange(1, 50) page: Int,
        @IntRange(1, 80) size: Int,
        sort: SortType = SortType.ACCURACY,
    ): Boolean

    suspend fun fetchVideo(
        query: String,
        @IntRange(1, 15) page: Int,
        @IntRange(1, 30) size: Int,
        sort: SortType = SortType.ACCURACY,
    ): Boolean
}