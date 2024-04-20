package com.dothebestmayb.nbc_challenge_kakaoapi.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.local.VideoDocument

@Dao
interface VideoDocumentDao {
    @Query("SELECT * FROM videoDocuments")
    suspend fun getAll(): List<VideoDocument>

    @Query("SELECT EXISTS(SELECT * FROM videoDocuments WHERE url = :url)")
    suspend fun isBookmarked(url: String): Boolean

    @Insert
    suspend fun insertAll(videoDocuments: List<VideoDocument>)

    @Delete
    suspend fun delete(videoDocument: VideoDocument)
}