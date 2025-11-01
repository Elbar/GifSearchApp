package com.sample.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sample.domain.model.Gif
import com.sample.presentation.extensions.formatDateTime
import com.sample.presentation.extensions.formatFileSize

@Composable
fun GifDetailContent(
    gif: Gif,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        GifDisplay(gif = gif)

        Spacer(modifier = Modifier.height(16.dp))

        GifTitle(title = gif.title)

        Spacer(modifier = Modifier.height(12.dp))

        AuthorAndRating(
            username = gif.username,
            rating = gif.rating
        )

        Spacer(modifier = Modifier.height(16.dp))

        ImageInformation(gif = gif)

        Spacer(modifier = Modifier.height(16.dp))

        AvailableFormats(gif = gif)
    }
}

@Composable
private fun GifDisplay(gif: Gif) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        AsyncImage(
            model = gif.images.original.url,
            contentDescription = gif.title,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun GifTitle(title: String) {
    Text(
        text = title.ifEmpty { "Untitled GIF" },
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun AuthorAndRating(
    username: String,
    rating: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (username.isNotEmpty()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Author",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = username,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = rating.uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ImageInformation(gif: Gif) {
    SectionTitle("Image Information")
    Spacer(modifier = Modifier.height(8.dp))

    InfoCard {
        InfoRow("Dimensions", "${gif.images.original.width} × ${gif.images.original.height}")
        InfoRow("File Size", gif.images.original.size.formatFileSize())
        if (gif.importDateTime.isNotEmpty()) {
            InfoRow("Import Date", gif.importDateTime.formatDateTime())
        }
        if (gif.trendingDateTime.isNotEmpty() && gif.trendingDateTime != "0000-00-00 00:00:00") {
            InfoRow("Trending Date", gif.trendingDateTime.formatDateTime())
        }
    }
}

@Composable
private fun AvailableFormats(gif: Gif) {
    SectionTitle("Available Formats")
    Spacer(modifier = Modifier.height(8.dp))

    InfoCard {
        InfoRow("Original", "${gif.images.original.width}×${gif.images.original.height}")
        InfoRow("Downsized", "${gif.images.downsized.width}×${gif.images.downsized.height}")
        InfoRow("Fixed Height", "${gif.images.fixedHeight.width}×${gif.images.fixedHeight.height}")
        InfoRow("Fixed Width", "${gif.images.fixedWidth.width}×${gif.images.fixedWidth.height}")
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun InfoCard(
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}