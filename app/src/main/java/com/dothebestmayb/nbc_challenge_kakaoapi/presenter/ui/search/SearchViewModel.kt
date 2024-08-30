package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.model.SearchInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.domain.repository.KakaoSearchRepository
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.network.ConnectivityObserver
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.network.NetworkStatus
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model.SearchItem
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search.model.SearchUiState
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.util.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
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

    private val _isFetchAllowed = MutableStateFlow(true)
    val isFetchAllowed: StateFlow<Boolean> = _isFetchAllowed.asStateFlow()

    private var currentSearchKeyword: String = ""
    private var imageSearchPage = 1
    private var videoSearchPage = 1
    private var size = 20
    private var retryCount = 0

    private var fetchJob: Job? = null
    private var collectResultJob: Job? = null

    init {
        setNetworkMonitor()
    }

    @OptIn(FlowPreview::class)
    private fun setNetworkMonitor() {
        viewModelScope.launch {
            connectivityObserver.observe().debounce(1000L).collect { status ->
                _uiState.emit(_uiState.value.copy(networkStatus = status))

                // 인터넷이 연결된 경우
                if (status == NetworkStatus.AVAILABLE) {
                    // 데이터가 비어있을 때 재로딩 시도
                    if (currentSearchKeyword.isNotBlank() && _uiState.value.searchResult.isEmpty()) {
                        onSearch(currentSearchKeyword, true)
                    } else if (_isFetchAllowed.value.not()) { // fetch 재시도 허용
                        retryCount = 0
                        _isFetchAllowed.emit(true)
                        fetchData()
                    }
                }
            }
        }
    }

    fun onBookmarkClick(item: SearchItem) {
        viewModelScope.launch {
            kakaoSearchRepository.updateItem(item.switchBookmarkState().toDomain())
        }
    }

    fun onSearch(keyword: String, isResearch: Boolean = false) {
        if (isResearch.not() && keyword == currentSearchKeyword) {
            return
        }
        resetSearchConfig(keyword)

        if (keyword.isBlank()) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                searchResult = emptyList(),
            )
            fetchJob?.cancel()
            return
        }
        collectResultAs(keyword)
        fetchData()
    }

    private fun resetSearchConfig(keyword: String) {
        currentSearchKeyword = keyword
        imageSearchPage = 0
        videoSearchPage = 0
        retryCount = 0
        _isFetchAllowed.value = true
    }

    @OptIn(FlowPreview::class)
    private fun collectResultAs(keyword: String) {
        collectResultJob?.cancel()

        collectResultJob = viewModelScope.launch {
            kakaoSearchRepository.getItems(keyword).distinctUntilChanged()
                .debounce(300L) // 이미지와 비디오 API 로딩에 시간 차이가 존재하여 설정
                .collectLatest { items ->
                    _uiState.emit(
                        _uiState.value.copy(
                            searchResult = items.map {
                                when (it) {
                                    is SearchInfo.Image -> it.toUi()
                                    is SearchInfo.Video -> it.toUi()
                                }
                            },
                        )
                    )
                }
        }
    }

    fun fetchData() {
        if (_uiState.value.isLoading || _isFetchAllowed.value.not()) {
            return
        }

        _uiState.value = _uiState.value.copy(
            isLoading = true,
        )

        fetchJob?.cancel()
        imageSearchPage++
        videoSearchPage++

        fetchJob = viewModelScope.launch {
            val image = async {
                kakaoSearchRepository.fetchImage(currentSearchKeyword, imageSearchPage, size)
            }
            val isFetchVideoSuccess =
                kakaoSearchRepository.fetchVideo(currentSearchKeyword, videoSearchPage, size)
            val isFetchImageSuccess = image.await()

            _isFetchAllowed.emit(isFetchVideoSuccess || isFetchImageSuccess || ++retryCount < MAX_RETRY_COUNT)
            _uiState.emit(
                _uiState.value.copy(
                    isLoading = false,
                )
            )
        }
    }

    companion object {
        private const val MAX_RETRY_COUNT = 5
    }
}