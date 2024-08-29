package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.VideoSearchEntity
import kotlinx.coroutines.flow.Flow

interface KakaoLocalDataSource {

    suspend fun insertImage(imageSearchEntities: List<ImageSearchEntity>)

    suspend fun insertVideo(videoSearchEntities: List<VideoSearchEntity>)

    suspend fun deleteAllImage(keyword: String)

    suspend fun deleteAllVideo(keyword: String)

    fun loadAllImage(keyword: String): Flow<List<ImageSearchEntity>>

    fun loadAllVideo(keyword: String): Flow<List<VideoSearchEntity>>
}