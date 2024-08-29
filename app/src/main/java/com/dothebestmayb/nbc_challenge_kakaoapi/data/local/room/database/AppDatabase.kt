package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao.SearchDao
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.VideoSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.util.DateConverter

@Database(entities = [ImageSearchEntity::class, VideoSearchEntity::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
}