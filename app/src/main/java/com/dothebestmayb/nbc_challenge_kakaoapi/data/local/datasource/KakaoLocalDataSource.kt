package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.ImageSearchEntity
import kotlinx.coroutines.flow.Flow

interface KakaoLocalDataSource {

    suspend fun insertImage(imageSearchEntity: List<ImageSearchEntity>)

    suspend fun deleteImage(imageSearchEntity: List<ImageSearchEntity>)

    fun loadAllImage(keyword: String): Flow<List<ImageSearchEntity>>
}