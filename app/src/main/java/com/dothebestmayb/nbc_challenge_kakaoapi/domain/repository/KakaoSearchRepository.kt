package com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository

import androidx.paging.PagingData
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.DocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity
import kotlinx.coroutines.flow.Flow

interface KakaoSearchRepository {

    fun getImage(
        query: String,
        size: Int,
    ): Flow<PagingData<DocumentEntity>>

    fun getVideo(
        query: String,
        size: Int,
    ): Flow<PagingData<VideoDocumentEntity>>
}