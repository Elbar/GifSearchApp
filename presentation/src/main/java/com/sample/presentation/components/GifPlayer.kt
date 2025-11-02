package com.sample.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.imageLoader

import coil.request.ImageRequest
import com.sample.domain.model.Gif

@Composable
fun GifPlayer(
    gif: Gif,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    showControls: Boolean = true,
    useOriginalQuality: Boolean = false
) {
    var isPlaying by remember { mutableStateOf(true) }
    val context = LocalContext.current
    
    val imageUrl = if (useOriginalQuality) {
        gif.images.original.url
    } else {
        gif.images.downsizedMedium.url
    }
    
    val staticImageUrl = gif.images.downsized.url.replace(".gif", ".webp")
    
    Box(modifier = modifier) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(if (isPlaying) imageUrl else staticImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = gif.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            imageLoader = context.imageLoader,
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            error = {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(gif.images.fixedHeight.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = gif.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale,
                    imageLoader = context.imageLoader
                )
            }
        )
        
        if (showControls) {
            PlayPauseControl(
                isPlaying = isPlaying,
                onToggle = { isPlaying = !isPlaying },
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
private fun PlayPauseControl(
    isPlaying: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .size(40.dp)
            .clip(CircleShape)
            .background(
                Color.Black.copy(alpha = 0.6f)
            )
            .clickable { onToggle() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = if (isPlaying) "Pause" else "Play",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}
