package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao.SearchDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkLocalDataSourceImpl @Inject constructor(
    private val searchDao: SearchDao
) : BookmarkLocalDataSource {

    override suspend fun updateItem(searchEntity: SearchEntity) {
        searchDao.updateItem(searchEntity)
    }

    override fun loadBookmarkedItem(): Flow<List<SearchEntity>> {
        return searchDao.loadBookmarkedItem()
    }
}