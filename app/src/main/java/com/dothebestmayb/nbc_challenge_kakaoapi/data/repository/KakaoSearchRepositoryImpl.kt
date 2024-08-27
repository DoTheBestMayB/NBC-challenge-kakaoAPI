package com.dothebestmayb.nbc_challenge_kakaoapi.data.repository

import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.datasource.KakaoRemoteDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.GetImageRequest
import com.dothebestmayb.nbc_challenge_kakaoapi.data.remote.model.SortType
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageSearchInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class KakaoSearchRepositoryImpl @Inject constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource,
): KakaoSearchRepository {

    override val data: Flow<List<ImageSearchInfo>>
        get() {
            TODO("로컬에 저장된 이미지 리턴")
        }

    override suspend fun fetchImage(query: String, sort: SortType, page: Int, size: Int) {
        try {
            val response = kakaoRemoteDataSource.getImage(GetImageRequest(query, sort, page, size))
            // TODO : 로컬에 저장하기
        } catch (_: Exception) {
        }
    }
}