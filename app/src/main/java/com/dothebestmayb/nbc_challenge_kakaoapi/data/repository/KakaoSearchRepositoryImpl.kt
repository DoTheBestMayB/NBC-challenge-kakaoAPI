package com.dothebestmayb.nbc_challenge_kakaoapi.data.repository

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource.KakaoLocalDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource.KakaoRemoteDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.service.KakaoService
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchHistoryInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class KakaoSearchRepositoryImpl @Inject constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource,
    private val kakaoLocalDataSource: KakaoLocalDataSource,
) : KakaoSearchRepository {

    private enum class SearchType {
        IMAGE, VIDEO
    }

    override fun getItems(query: String): Flow<List<SearchInfo>> {
        return kakaoLocalDataSource.loadAllItem(query).map { items ->
            items.map { entity ->
                when (entity.type) {
                    SearchEntity.SearchType.IMAGE -> SearchInfo.Image(
                        thumbnailUrl = entity.thumbnailUrl,
                        imageUrl = entity.url,
                        displaySiteName = entity.displaySiteName.orEmpty(),
                        docUrl = entity.docUrl.orEmpty(),
                        datetime = entity.datetime,
                        searchKeyword = query,
                        bookmarked = entity.bookmarked,
                    )

                    SearchEntity.SearchType.VIDEO -> SearchInfo.Video(
                        title = entity.title.orEmpty(),
                        url = entity.url,
                        datetime = entity.datetime,
                        playTime = entity.playTime ?: 0,
                        thumbnail = entity.thumbnailUrl,
                        searchKeyword = query,
                        bookmarked = entity.bookmarked,
                    )
                }
            }
        }
    }

    override fun getSearchHistory(): Flow<List<SearchHistoryInfo>> {
        return kakaoLocalDataSource.getSearchHistory().map { items ->
            items.map {
                it.toDomain()
            }
        }
    }

    override suspend fun fetchImage(query: String, page: Int, size: Int, sort: SortType): Boolean {
        if (!checkRequestParameterValid(page, size, SearchType.IMAGE)) {
            return false
        }

        try {
            val response = kakaoRemoteDataSource.getImage(query, sort, page, size)
            kakaoLocalDataSource.insertItem(response.documents.map {
                SearchEntity(
                    url = it.imageUrl,
                    type = SearchEntity.SearchType.IMAGE,
                    searchKeyword = query,
                    thumbnailUrl = it.thumbnailUrl,
                    displaySiteName = it.displaySiteName,
                    docUrl = it.docUrl,
                    datetime = it.datetime,
                    bookmarked = false,
                )
            })
        } catch (_: Exception) {
            return false
        }
        return true
    }

    override suspend fun fetchVideo(query: String, page: Int, size: Int, sort: SortType): Boolean {
        if (!checkRequestParameterValid(page, size, SearchType.VIDEO)) {
            return false
        }

        try {
            val response = kakaoRemoteDataSource.getVideo(query, sort, page, size)
            kakaoLocalDataSource.insertItem(response.documents.map {
                SearchEntity(
                    url = it.url,
                    type = SearchEntity.SearchType.VIDEO,
                    searchKeyword = query,
                    thumbnailUrl = it.thumbnail,
                    title = it.title,
                    datetime = it.datetime,
                    playTime = it.playTime,
                    bookmarked = false,
                )
            })
        } catch (_: Exception) {
            return false
        }
        return true
    }

    private fun checkRequestParameterValid(page: Int, size: Int, type: SearchType): Boolean {
        return when (type) {
            SearchType.IMAGE -> page in KakaoService.IMAGE_MIN_PAGE_INDEX..KakaoService.IMAGE_MAX_PAGE_INDEX && size in KakaoService.IMAGE_MIN_DATA_SIZE..KakaoService.IMAGE_MAX_DATA_SIZE
            SearchType.VIDEO -> page in KakaoService.VIDEO_MIN_PAGE_INDEX..KakaoService.VIDEO_MAX_PAGE_INDEX && size in KakaoService.VIDEO_MIN_DATA_SIZE..KakaoService.VIDEO_MAX_DATA_SIZE
        }
    }

    override suspend fun updateItem(searchInfo: SearchInfo) {
        kakaoLocalDataSource.updateItem(searchInfo.toEntity())
    }

    override suspend fun addHistory(query: String) {
        kakaoLocalDataSource.addHistory(query)
    }

    override suspend fun deleteHistory(query: String) {
        kakaoLocalDataSource.deleteHistory(query)
    }
}