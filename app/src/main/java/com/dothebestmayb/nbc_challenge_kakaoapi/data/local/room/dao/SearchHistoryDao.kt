package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Query("DELETE FROM SearchHistoryEntity WHERE `query` = :query")
    fun delete(query: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(searchHistoryEntity: SearchHistoryEntity)

    @Query("SELECT * FROM SearchHistoryEntity ORDER BY datetime DESC")
    fun loadAll(): Flow<List<SearchHistoryEntity>>
}