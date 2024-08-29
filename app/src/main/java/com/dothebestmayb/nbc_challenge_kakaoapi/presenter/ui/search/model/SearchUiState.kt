package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model

import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.network.NetworkStatus

data class SearchUiState(
    val isLoading: Boolean = false,
    val searchResult: List<SearchItem> = emptyList(),
    val networkStatus: NetworkStatus = NetworkStatus.LOST,
)
