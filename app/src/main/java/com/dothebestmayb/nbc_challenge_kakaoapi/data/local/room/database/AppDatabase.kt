package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao.SearchDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao.SearchHistoryDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchHistoryEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.util.DateConverter

@Database(entities = [SearchEntity::class, SearchHistoryEntity::class], version = 3)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao

    abstract fun searchHistoryDao(): SearchHistoryDao
}