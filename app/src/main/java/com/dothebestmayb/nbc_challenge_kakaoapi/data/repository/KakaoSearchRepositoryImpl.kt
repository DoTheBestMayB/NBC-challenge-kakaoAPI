package com.dothebestmayb.nbc_challenge_kakaoapi.data.repository

import com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource.KakaoRemoteDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.map
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.ImageSearchEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.NetworkResult
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository

class KakaoSearchRepositoryImpl(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource,
) : KakaoSearchRepository {
    override suspend fun getImage(
        query: String,
        page: Int,
        size: Int
    ): NetworkResult<ImageSearchEntity> {
        return kakaoRemoteDataSource.getImage(query, page, size).map {
            it.toEntity()
        }
    }
}