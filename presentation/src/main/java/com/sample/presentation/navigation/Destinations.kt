package com.sample.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

object Destinations {
    const val SEARCH_ROUTE = "search"
    const val DETAIL_ROUTE = "detail/{gifId}"
    
    fun detailRoute(gifId: String) = "detail/$gifId"
}

@Composable
fun GifSearchNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.SEARCH_ROUTE
    ) {

    }
}