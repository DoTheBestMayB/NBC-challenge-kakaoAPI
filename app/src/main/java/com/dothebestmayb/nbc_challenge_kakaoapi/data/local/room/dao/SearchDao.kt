package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.VideoSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(imageSearchEntities: List<ImageSearchEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVideo(videoSearchEntities: List<VideoSearchEntity>)

    @Query("DELETE FROM ImageSearchEntity WHERE search_keyword = :keyword")
    suspend fun deleteAllImage(keyword: String)

    @Query("DELETE FROM VideoSearchEntity WHERE search_keyword = :keyword")
    suspend fun deleteAllVideo(keyword: String)

    @Query("SELECT * FROM ImageSearchEntity WHERE search_keyword = :keyword")
    fun loadAllImage(keyword: String): Flow<List<ImageSearchEntity>>

    @Query("SELECT * FROM VideoSearchEntity WHERE search_keyword = :keyword")
    fun loadAllVideo(keyword: String): Flow<List<VideoSearchEntity>>
}