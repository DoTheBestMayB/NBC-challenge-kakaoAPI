package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao.SearchDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.VideoSearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoLocalDataSourceImpl @Inject constructor(
    private val searchDao: SearchDao,
) : KakaoLocalDataSource {
    override suspend fun insertImage(imageSearchEntities: List<ImageSearchEntity>) {
        searchDao.insertImage(imageSearchEntities)
    }

    override suspend fun insertVideo(videoSearchEntities: List<VideoSearchEntity>) {
        searchDao.insertVideo(videoSearchEntities)
    }

    override suspend fun deleteAllImage(keyword: String) {
        searchDao.deleteAllImage(keyword)
    }

    override suspend fun deleteAllVideo(keyword: String) {
        searchDao.deleteAllVideo(keyword)
    }

    override fun loadAllImage(keyword: String): Flow<List<ImageSearchEntity>> {
        return searchDao.loadAllImage(keyword)
    }

    override fun loadAllVideo(keyword: String): Flow<List<VideoSearchEntity>> {
        return searchDao.loadAllVideo(keyword)
    }
}