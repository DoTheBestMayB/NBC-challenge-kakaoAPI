package com.dothebestmayb.nbc_challenge_kakaoapi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.local.ImageDocument
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.local.VideoDocument

@Database(entities = [ImageDocument::class, VideoDocument::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDocumentDao(): ImageDocumentDao
    abstract fun videoDocumentDao(): VideoDocumentDao
}