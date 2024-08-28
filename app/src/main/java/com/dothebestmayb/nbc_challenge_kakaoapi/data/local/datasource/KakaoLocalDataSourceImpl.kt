package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao.SearchDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.ImageSearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoLocalDataSourceImpl @Inject constructor(
    private val searchDao: SearchDao,
): KakaoLocalDataSource {
    override suspend fun insertImage(imageSearchEntity: List<ImageSearchEntity>) {
        searchDao.insertImage(imageSearchEntity)
    }

    override suspend fun deleteImage(imageSearchEntity: List<ImageSearchEntity>) {
        searchDao.deleteImage(imageSearchEntity)
    }

    override fun loadAllImage(keyword: String): Flow<List<ImageSearchEntity>> {
        return searchDao.loadAllImage(keyword)
    }
}