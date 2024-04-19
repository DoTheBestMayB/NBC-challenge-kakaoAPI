package com.dothebestmayb.nbc_challenge_kakaoapi.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDocumentDao {
    @Query("SELECT * FROM imageDocuments")
    suspend fun getAll(): List<ImageDocument>

    @Query("SELECT EXISTS(SELECT * FROM imageDocuments WHERE doc_url = :docUrl)")
    suspend fun isBookmarked(docUrl: String): Boolean

    @Insert
    suspend fun insertAll(imageDocuments: List<ImageDocument>)

    @Delete
    suspend fun delete(imageDocument: ImageDocument)
}