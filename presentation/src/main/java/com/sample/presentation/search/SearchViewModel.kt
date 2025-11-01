package com.sample.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sample.domain.model.Gif
import com.sample.domain.network.NetworkStatus
import com.sample.domain.usecase.GetTrendingGifsUseCase
import com.sample.domain.usecase.NetworkConnectivityObserverUseCase
import com.sample.domain.usecase.SearchGifsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val searchGifsUseCase: SearchGifsUseCase,
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase,
    private val networkConnectivityObserverUseCase: NetworkConnectivityObserverUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _networkStatus = MutableStateFlow(NetworkStatus.Available)
    val networkStatus: StateFlow<NetworkStatus> = _networkStatus.asStateFlow()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    val gifs: StateFlow<PagingData<Gif>> = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                getTrendingGifsUseCase()
            } else {
                searchGifsUseCase(query)
            }
        }
        .cachedIn(viewModelScope)
        .let { flow ->
            val mutableStateFlow = MutableStateFlow(PagingData.empty<Gif>())
            viewModelScope.launch {
                flow.collect { pagingData ->
                    mutableStateFlow.value = pagingData
                }
            }
            mutableStateFlow.asStateFlow()
        }

    init {
        viewModelScope.launch {
            networkConnectivityObserverUseCase.observe().collect { status ->
                _networkStatus.value = status
                _uiState.value = _uiState.value.copy(
                    isNetworkAvailable = status == NetworkStatus.Available
                )
            }
        }

        onSearchQueryChange("")
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _uiState.value = _uiState.value.copy(
            isSearching = query.isNotBlank()
        )
    }


    fun onRetry() {
        val currentQuery = _searchQuery.value
        _searchQuery.value = ""
        _searchQuery.value = currentQuery
    }
}

