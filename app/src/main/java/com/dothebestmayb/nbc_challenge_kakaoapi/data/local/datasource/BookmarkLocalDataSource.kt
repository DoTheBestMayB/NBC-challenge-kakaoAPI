package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkLocalDataSource {

    suspend fun updateItem(searchEntity: SearchEntity)

    fun loadBookmarkedItem(): Flow<List<SearchEntity>>
}