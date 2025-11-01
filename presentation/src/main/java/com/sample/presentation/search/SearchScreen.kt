package com.sample.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.sample.domain.model.Gif
import com.sample.domain.network.NetworkStatus
import com.sample.presentation.composables.ErrorMessage
import com.sample.presentation.composables.GifGrid
import com.sample.presentation.composables.LoadingIndicator
import com.sample.presentation.composables.NetworkStatusBanner
import com.sample.presentation.composables.SearchBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onGifClick: (Gif) -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val networkStatus by viewModel.networkStatus.collectAsState()
    val gifs = viewModel.gifs.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        gifs.refresh()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GIF Search") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                modifier = Modifier.fillMaxWidth()
            )

            if (networkStatus != NetworkStatus.Available) {
                NetworkStatusBanner(
                    networkStatus = networkStatus,
                    onRetry = viewModel::onRetry
                )
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    gifs.loadState.refresh is LoadState.Loading -> {
                        LoadingIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            "Loading GIFs..."
                        )
                    }

                    gifs.loadState.refresh is LoadState.Error -> {
                        ErrorMessage(
                            message = (gifs.loadState.refresh as LoadState.Error).error.message
                                ?: "An error occurred",
                            onRetry = { gifs.retry() },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    else -> {
                        GifGrid(
                            gifs = gifs,
                            onGifClick = onGifClick
                        )
                    }
                }

                if (gifs.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}




