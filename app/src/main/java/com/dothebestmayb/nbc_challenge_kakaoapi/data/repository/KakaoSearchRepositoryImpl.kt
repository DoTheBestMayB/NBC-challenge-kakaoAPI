package com.dothebestmayb.nbc_challenge_kakaoapi.data.repository

import android.util.Log
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource.KakaoLocalDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.SearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource.KakaoRemoteDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class KakaoSearchRepositoryImpl @Inject constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource,
    private val kakaoLocalDataSource: KakaoLocalDataSource,
) : KakaoSearchRepository {

    override suspend fun getItems(query: String): Flow<List<SearchInfo>> {
        return kakaoLocalDataSource.loadAllItem(query).map { items ->
            items.map { entity ->
                when (entity.type) {
                    SearchEntity.SearchType.IMAGE -> SearchInfo.ImageSearchInfo(
                        thumbnailUrl = entity.thumbnailUrl,
                        imageUrl = entity.url,
                        displaySiteName = entity.displaySiteName.orEmpty(),
                        docUrl = entity.docUrl.orEmpty(),
                        datetime = entity.datetime
                    )

                    SearchEntity.SearchType.VIDEO -> SearchInfo.VideoSearchInfo(
                        title = entity.title.orEmpty(),
                        url = entity.url,
                        datetime = entity.datetime,
                        playTime = entity.playTime ?: 0,
                        thumbnail = entity.thumbnailUrl,
                    )
                }
            }
        }
    }

    override suspend fun fetchImage(query: String, page: Int, size: Int, sort: SortType) {
        try {
            val response = kakaoRemoteDataSource.getImage(query, sort, page, size)
            kakaoLocalDataSource.insertImage(response.documents.map {
                SearchEntity(
                    url = it.imageUrl,
                    type = SearchEntity.SearchType.IMAGE,
                    searchKeyword = query,
                    thumbnailUrl = it.thumbnailUrl,
                    displaySiteName = it.displaySiteName,
                    docUrl = it.docUrl,
                    datetime = it.datetime,
                )
            })
        } catch (_: Exception) {
        }
    }

    override suspend fun fetchVideo(query: String, page: Int, size: Int, sort: SortType) {
        try {
            val response = kakaoRemoteDataSource.getVideo(query, sort, page, size)
            kakaoLocalDataSource.insertVideo(response.documents.map {
                SearchEntity(
                    url = it.url,
                    type = SearchEntity.SearchType.VIDEO,
                    searchKeyword = query,
                    thumbnailUrl = it.thumbnail,
                    title = it.title,
                    datetime = it.datetime,
                    playTime = it.playTime,
                )
            })
        } catch (_: Exception) {
        }
    }
}