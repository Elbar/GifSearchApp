package com.sample.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.presentation.detail.DetailScreen
import com.sample.presentation.search.SearchScreen

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
        composable(Destinations.SEARCH_ROUTE) {
            SearchScreen(
                onGifClick = { gif ->
                    navController.navigate(Destinations.detailRoute(gif.id))
                }
            )
        }

        composable(Destinations.DETAIL_ROUTE) { backStackEntry ->
            val gifId = backStackEntry.arguments?.getString("gifId") ?: return@composable
            DetailScreen(
                gifId = gifId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}