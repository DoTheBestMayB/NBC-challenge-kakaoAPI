package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(imageSearchEntities: List<SearchEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVideo(videoSearchEntities: List<SearchEntity>)

    @Query("DELETE FROM SearchEntity WHERE search_keyword = :keyword")
    suspend fun deleteAll(keyword: String)

    @Query("SELECT * FROM SearchEntity WHERE search_keyword = :keyword")
    fun loadAll(keyword: String): Flow<List<SearchEntity>>
}