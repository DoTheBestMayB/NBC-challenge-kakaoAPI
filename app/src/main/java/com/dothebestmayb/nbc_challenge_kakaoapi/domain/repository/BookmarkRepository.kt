package com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository

import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.BookmarkInfo
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun updateItem(bookmarkInfo: BookmarkInfo)

    fun loadBookmarkedItem(): Flow<List<BookmarkInfo>>
}