package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.util.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val kakaoSearchRepository: KakaoSearchRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var currentSearchKeyword: String = ""
    private var page = 1
    private var size = 80

    fun onBookmarkClick(item: SearchItem) {
        viewModelScope.launch {
            // TODO : 북마크 구현
        }
    }

    fun onSearch(keyword: String) {
        if (keyword == currentSearchKeyword) {
            return
        }
        currentSearchKeyword = keyword
        _uiState.value = SearchUiState(
            isLoading = true,
            searchResult = emptyList(),
            error = SearchErrorType.NONE,
        )

        viewModelScope.launch {
            kakaoSearchRepository.fetchImage(keyword, page, size)

            kakaoSearchRepository.getItems(currentSearchKeyword).distinctUntilChanged().collectLatest { items ->
                _uiState.emit(
                    _uiState.value.copy(
                        isLoading = false,
                        searchResult = items.map { it.toUi() },
                    )
                )
            }
        }
    }
}