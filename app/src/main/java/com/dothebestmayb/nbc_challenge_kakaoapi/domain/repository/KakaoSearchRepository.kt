package com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository

import androidx.annotation.IntRange
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageSearchInfo
import kotlinx.coroutines.flow.Flow

interface KakaoSearchRepository {

    suspend fun getItems(query: String): Flow<List<ImageSearchInfo>>

    suspend fun fetchImage(
        query: String,
        sort: SortType = SortType.ACCURACY,
        @IntRange(1, 50) page: Int,
        @IntRange(1, 80) size: Int,
    )
}