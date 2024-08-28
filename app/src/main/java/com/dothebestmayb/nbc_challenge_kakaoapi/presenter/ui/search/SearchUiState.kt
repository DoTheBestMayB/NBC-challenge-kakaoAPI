package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

data class SearchUiState(
    val isLoading: Boolean = false,
    val searchResult: List<SearchItem> = emptyList(),
    val error: SearchErrorType = SearchErrorType.NONE,
)
