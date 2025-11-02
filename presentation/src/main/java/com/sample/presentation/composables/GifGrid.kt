package com.sample.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.sample.domain.model.Gif

@Composable
fun GifGrid(
    gifs: androidx.paging.compose.LazyPagingItems<Gif>,
    onGifClick: (Gif) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val columns = if (screenWidth > 600.dp) 3 else 2

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = gifs.itemCount,
        ) { index ->
            val gif = gifs[index]
            if (gif != null) {
                GifItem(
                    gif = gif,
                    onClick = { onGifClick(gif) }
                )
            }
        }
    }
}