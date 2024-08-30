package com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    // UPSERT를 이용하면 기존의 북마크 여부를 보존할 수 없어서 이렇게 구현
    // 아래 코드는 UPSERT와 완전히 동일하지는 않다. https://stackoverflow.com/a/4253806/11722881
    @Query(
        """
        INSERT OR REPLACE INTO SearchEntity (
            url, search_keyword, thumbnail_url, datetime, type, 
            display_site_name, doc_url, title, play_time, bookmarked
        ) 
        SELECT
            :url, :searchKeyword, :thumbnailUrl, :datetime, :type,
            :displaySiteName, :docUrl, :title, :playTime,
            CASE 
                WHEN (SELECT bookmarked FROM SearchEntity WHERE url = :url) = 1 THEN 1 
                ELSE :bookmarked 
            END
        WHERE NOT EXISTS (
            SELECT 1 FROM SearchEntity WHERE url = :url AND bookmarked = 1
        ) OR :url NOT IN (SELECT url FROM SearchEntity)
    """
    )
    suspend fun insertSearchEntity(
        url: String,
        searchKeyword: String?,
        thumbnailUrl: String?,
        datetime: String,
        type: String,
        displaySiteName: String?,
        docUrl: String?,
        title: String?,
        playTime: Int?,
        bookmarked: Boolean
    )

    @Query("DELETE FROM SearchEntity WHERE search_keyword = :keyword AND bookmarked = 0")
    suspend fun deleteCached(keyword: String)

    @Update
    suspend fun updateItem(searchEntity: SearchEntity)

    @Query("SELECT * FROM SearchEntity WHERE search_keyword = :keyword ORDER BY datetime DESC")
    fun loadAll(keyword: String): Flow<List<SearchEntity>>

    @Query("SELECT * FROM SearchEntity WHERE bookmarked = 1")
    fun loadBookmarkedItem(): Flow<List<SearchEntity>>
}