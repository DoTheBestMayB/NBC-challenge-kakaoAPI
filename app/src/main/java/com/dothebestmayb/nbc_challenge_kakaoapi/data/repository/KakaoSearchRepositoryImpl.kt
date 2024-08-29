package com.dothebestmayb.nbc_challenge_kakaoapi.data.repository

import android.util.Log
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource.KakaoLocalDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.VideoSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource.KakaoRemoteDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

internal class KakaoSearchRepositoryImpl @Inject constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource,
    private val kakaoLocalDataSource: KakaoLocalDataSource,
) : KakaoSearchRepository {

    override suspend fun getItems(query: String): Flow<List<SearchInfo>> {
        return merge(kakaoLocalDataSource.loadAllImage(query).map { items ->
            items.map {
                it.toDomain()
            }
        }, kakaoLocalDataSource.loadAllVideo(query).map { items ->
            items.map {
                it.toDomain()
            }
        })
    }

    override suspend fun fetchImage(query: String, page: Int, size: Int, sort: SortType) {
        try {
            val response = kakaoRemoteDataSource.getImage(query, sort, page, size)
            kakaoLocalDataSource.insertImage(response.documents.map {
                ImageSearchEntity(
                    imageUrl = it.imageUrl,
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
                VideoSearchEntity(
                    title = it.title,
                    searchKeyword = query,
                    url = it.url,
                    datetime = it.datetime,
                    playTime = it.playTime,
                    thumbnail = it.thumbnail,
                )
            })
        } catch (_: Exception) {
        }
    }
}