package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import kotlinx.coroutines.flow.Flow

interface KakaoLocalDataSource {

    suspend fun insertImage(imageSearchEntities: List<SearchEntity>)

    suspend fun insertVideo(videoSearchEntities: List<SearchEntity>)

    suspend fun deleteAll(keyword: String)

    fun loadAllItem(keyword: String): Flow<List<SearchEntity>>
}