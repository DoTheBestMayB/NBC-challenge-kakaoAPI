package com.dothebestmayb.nbc_challenge_kakaoapi.data.repository

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource.BookmarkLocalDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.toSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.BookmarkInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkLocalDataSource: BookmarkLocalDataSource,
) : BookmarkRepository {

    override suspend fun updateItem(bookmarkInfo: BookmarkInfo) {
        bookmarkLocalDataSource.updateItem(bookmarkInfo.toSearchEntity())
    }

    override fun loadBookmarkedItem(): Flow<List<BookmarkInfo>> {
        return bookmarkLocalDataSource.loadBookmarkedItem().map { items ->
            items.map { entity ->
                when (entity.type) {
                    SearchEntity.SearchType.IMAGE -> BookmarkInfo.Image(
                        thumbnailUrl = entity.thumbnailUrl,
                        imageUrl = entity.url,
                        displaySiteName = entity.displaySiteName.orEmpty(),
                        docUrl = entity.docUrl.orEmpty(),
                        datetime = entity.datetime,
                        searchKeyword = entity.searchKeyword,
                        bookmarked = entity.bookmarked,
                    )

                    SearchEntity.SearchType.VIDEO -> BookmarkInfo.Video(
                        title = entity.title.orEmpty(),
                        url = entity.url,
                        datetime = entity.datetime,
                        playTime = entity.playTime ?: 0,
                        thumbnail = entity.thumbnailUrl,
                        searchKeyword = entity.searchKeyword,
                        bookmarked = entity.bookmarked,
                    )
                }
            }
        }
    }
}