package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao.SearchDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoLocalDataSourceImpl @Inject constructor(
    private val searchDao: SearchDao,
) : KakaoLocalDataSource {
    override suspend fun insertEntities(searchEntities: List<SearchEntity>) {
        searchDao.insertSearchEntity(searchEntities)
    }

    override suspend fun insertVideo(videoSearchEntities: List<SearchEntity>) {
        searchDao.insertVideo(videoSearchEntities)
    }

    override suspend fun deleteAll(keyword: String) {
        searchDao.deleteAll(keyword)
    }

    override fun loadAllItem(keyword: String): Flow<List<SearchEntity>> {
        return searchDao.loadAll(keyword)
    }
}