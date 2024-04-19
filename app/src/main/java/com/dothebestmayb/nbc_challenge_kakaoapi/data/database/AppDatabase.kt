package com.dothebestmayb.nbc_challenge_kakaoapi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ImageDocument::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDocumentDao(): ImageDocumentDao
}