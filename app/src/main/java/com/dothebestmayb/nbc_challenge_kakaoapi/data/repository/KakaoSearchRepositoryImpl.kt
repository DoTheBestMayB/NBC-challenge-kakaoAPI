package com.dothebestmayb.nbc_challenge_kakaoapi.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource.KakaoRemoteDataSource
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.ImageDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.VideoDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.util.toEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.DocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class KakaoSearchRepositoryImpl(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource,
) : KakaoSearchRepository {
    override fun getImage(
        query: String,
        size: Int
    ): Flow<PagingData<DocumentEntity>> {
        return kakaoRemoteDataSource.getImage(query, size).map {
            it.map {
                when(it) {
                    is ImageDocumentResponse -> it.toEntity()
                    is VideoDocumentResponse -> it.toEntity()
                }
            }
        }
    }

    override fun getVideo(
        query: String,
        size: Int
    ): Flow<PagingData<VideoDocumentEntity>> {
        return kakaoRemoteDataSource.getVideo(query, size).map {
            it.map { it.toEntity() }
        }
    }
}