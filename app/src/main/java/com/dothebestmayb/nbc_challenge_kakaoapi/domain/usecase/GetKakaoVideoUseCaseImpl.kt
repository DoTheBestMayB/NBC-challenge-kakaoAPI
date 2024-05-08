package com.dothebestmayb.nbc_challenge_kakaoapi.domain.usecase

import androidx.paging.PagingData
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.VideoDocumentEntity
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import kotlinx.coroutines.flow.Flow

class GetKakaoVideoUseCaseImpl(
    private val kakaoSearchRepository: KakaoSearchRepository,
) : GetKakaoVideoUseCase {
    override fun invoke(
        query: String,
        size: Int
    ): Flow<PagingData<VideoDocumentEntity>> {
        return kakaoSearchRepository.getVideo(query, size)
    }
}