package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface KakaoLocalDataSource {

    suspend fun insertItem(searchEntities: List<SearchEntity>)

    suspend fun deleteCachedItem(keyword: String)

    suspend fun updateItem(searchEntity: SearchEntity)

    suspend fun addHistory(query: String)

    suspend fun deleteHistory(query: String)

    fun loadAllItem(keyword: String): Flow<List<SearchEntity>>

    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>
}