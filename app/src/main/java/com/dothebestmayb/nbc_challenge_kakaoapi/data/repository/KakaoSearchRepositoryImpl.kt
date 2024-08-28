package com.dothebestmayb.nbc_challenge_kakaoapi.data.repository

import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.datasource.KakaoLocalDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.local.room.entity.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource.KakaoRemoteDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.GetImageRequest
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageSearchInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class KakaoSearchRepositoryImpl @Inject constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource,
    private val kakaoLocalDataSource: KakaoLocalDataSource,
): KakaoSearchRepository {

    override suspend fun getItems(query: String): Flow<List<ImageSearchInfo>> {
        return kakaoLocalDataSource.loadAllImage(query).map { items ->
            items.map {
                it.toDomain()
            }
        }
    }

    override suspend fun fetchImage(query: String, sort: SortType, page: Int, size: Int) {
        try {
            val response = kakaoRemoteDataSource.getImage(GetImageRequest(query, sort, page, size))
            kakaoLocalDataSource.insertImage(response.documents.map {
                ImageSearchEntity(
                    imageUrl = it.imageUrl,
                    searchKeyword = query,
                    thumbnailUrl = it.thumbnailUrl,
                    displaySiteName = it.displaySiteName,
                    docUrl = it.docUrl,
                    datetime =it.datetime,
                )
            })
        } catch (_: Exception) {
        }
    }
}