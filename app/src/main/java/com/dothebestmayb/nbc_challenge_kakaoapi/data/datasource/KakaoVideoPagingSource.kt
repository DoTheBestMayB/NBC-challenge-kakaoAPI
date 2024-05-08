package com.dothebestmayb.nbc_challenge_kakaoapi.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dothebestmayb.nbc_challenge_kakaoapi.data.model.remote.VideoDocumentResponse
import com.dothebestmayb.nbc_challenge_kakaoapi.data.network.KakaoService

class KakaoVideoPagingSource(
    private val service: KakaoService,
    private val query: String,
    private val size: Int,
) : PagingSource<Int, VideoDocumentResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoDocumentResponse> {
        val page = params.key ?: KAKAO_STARTING_PAGE_INDEX
        return try {
            val videos = service.getVideo(query, page, size)
            return LoadResult.Page(
                data = videos.documents,
                prevKey = if (page == KAKAO_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = page + 1,
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, VideoDocumentResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val KAKAO_STARTING_PAGE_INDEX = 1
    }
}