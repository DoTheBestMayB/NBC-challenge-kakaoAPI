package com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository

import androidx.annotation.IntRange
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchHistoryInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo
import kotlinx.coroutines.flow.Flow

interface KakaoSearchRepository {

    fun getItems(query: String): Flow<List<SearchInfo>>

    fun getSearchHistory(): Flow<List<SearchHistoryInfo>>

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

    suspend fun updateItem(searchInfo: SearchInfo)

    suspend fun addHistory(query: String)

    suspend fun deleteHistory(query: String)
}