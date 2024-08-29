package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.network.ConnectivityObserver
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
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var currentSearchKeyword: String = ""
    private var page = 1
    private var size = 20

    init {
        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                _uiState.emit(_uiState.value.copy(networkStatus = status))

                // 인터넷이 다시 연결된 경우, 데이터가 비어있을 때 재로딩 시도
                if (_uiState.value.searchResult.isEmpty()) {
                    onSearch(currentSearchKeyword, true)
                }
            }
        }
    }

    fun onBookmarkClick(item: SearchItem) {
        viewModelScope.launch {
            // TODO : 북마크 구현
        }
    }

    fun onSearch(keyword: String, isResearch: Boolean = false) {
        if (isResearch.not() && keyword == currentSearchKeyword) {
            return
        }
        currentSearchKeyword = keyword
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            searchResult = emptyList(),
        )

        viewModelScope.launch {
            kakaoSearchRepository.fetchImage(keyword, page, size)

            kakaoSearchRepository.getItems(currentSearchKeyword).distinctUntilChanged()
                .collectLatest { items ->
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