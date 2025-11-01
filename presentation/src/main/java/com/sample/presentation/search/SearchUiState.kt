package com.sample.presentation.search

data class SearchUiState(
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val isNetworkAvailable: Boolean = true,
    val errorMessage: String? = null
)