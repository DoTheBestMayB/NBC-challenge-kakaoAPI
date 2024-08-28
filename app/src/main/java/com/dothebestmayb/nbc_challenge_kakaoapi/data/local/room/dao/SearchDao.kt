package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.ImageSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageSearchEntity: List<ImageSearchEntity>)

    @Delete
    suspend fun deleteImage(imageSearchEntity: List<ImageSearchEntity>)

    @Query("SELECT * FROM imagesearchentity WHERE search_keyword = :keyword")
    fun loadAllImage(keyword: String): Flow<List<ImageSearchEntity>>
}