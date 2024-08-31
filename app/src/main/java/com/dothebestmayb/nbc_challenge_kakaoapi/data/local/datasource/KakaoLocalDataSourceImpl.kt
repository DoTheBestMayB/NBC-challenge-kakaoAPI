package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao.SearchDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao.SearchHistoryDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class KakaoLocalDataSourceImpl @Inject constructor(
    private val searchDao: SearchDao,
    private val searchHistoryDao: SearchHistoryDao,
) : KakaoLocalDataSource {
    override suspend fun insertItem(searchEntities: List<SearchEntity>) {
        for (searchEntity in searchEntities) {
            searchDao.insertSearchEntity(
                searchEntity.url,
                searchEntity.searchKeyword,
                searchEntity.thumbnailUrl,
                searchEntity.datetime.toString(),
                searchEntity.type.toString(),
                searchEntity.displaySiteName,
                searchEntity.docUrl,
                searchEntity.title,
                searchEntity.playTime,
                searchEntity.bookmarked
            )
        }
    }

    override suspend fun deleteCachedItem(keyword: String) {
        searchDao.deleteCached(keyword)
    }

    override suspend fun updateItem(searchEntity: SearchEntity) {
        searchDao.updateItem(searchEntity)
    }

    override suspend fun addHistory(query: String) {
        searchHistoryDao.add(
            SearchHistoryEntity(
                query = query,
                datetime = LocalDateTime.now(),
            )
        )
    }

    override suspend fun deleteHistory(query: String) {
        searchDao.deleteCached(query)
        searchHistoryDao.delete(query)
    }

    override fun loadAllItem(keyword: String): Flow<List<SearchEntity>> {
        return searchDao.loadAll(keyword)
    }

    override fun getSearchHistory(): Flow<List<SearchHistoryEntity>> {
        return searchHistoryDao.loadAll()
    }
}