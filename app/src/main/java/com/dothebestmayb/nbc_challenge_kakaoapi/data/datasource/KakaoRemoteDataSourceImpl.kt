package com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.ImageDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.SearchDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.VideoDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.network.KakaoService
import kotlinx.coroutines.flow.Flow

class KakaoRemoteDataSourceImpl(
    private val kakaoService: KakaoService
) : KakaoRemoteDataSource {
    override fun getImage(
        query: String,
        size: Int
    ): Flow<PagingData<SearchDocumentResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { KakaoPagingSource(kakaoService, query, size) }
        ).flow
    }

    override fun getVideo(
        query: String,
        size: Int
    ): Flow<PagingData<VideoDocumentResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { KakaoVideoPagingSource(kakaoService, query, size) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}