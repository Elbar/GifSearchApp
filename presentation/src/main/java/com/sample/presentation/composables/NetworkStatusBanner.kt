package com.sample.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.domain.network.NetworkStatus

@Composable
fun NetworkStatusBanner(
    networkStatus: NetworkStatus,
    onRetry: () -> Unit
) {
    val message = when (networkStatus) {
        NetworkStatus.Unavailable -> "No internet connection"
        NetworkStatus.Lost -> "Internet connection lost"
        NetworkStatus.Losing -> "Poor internet connection"
        NetworkStatus.Available -> ""
    }

    if (message.isNotEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = onRetry,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Retry")
                }
            }
        }
    }
}