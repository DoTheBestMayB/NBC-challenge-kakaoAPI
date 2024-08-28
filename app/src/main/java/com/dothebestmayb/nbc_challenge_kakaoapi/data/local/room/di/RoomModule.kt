package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.di

import android.content.Context
import androidx.room.Room
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context:Context
    ):AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "kakao-challege-database"
        ).build()
    }
}