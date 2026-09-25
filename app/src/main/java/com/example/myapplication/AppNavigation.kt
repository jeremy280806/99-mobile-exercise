package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavigation(viewModel: ListingViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "search_result") {

        composable("search_result") {
            SearchResultScreen(
                viewModel = viewModel,
                onListingClick = { idRumah ->
                    navController.navigate("listing_detail/$idRumah")
                }
            )
        }

        composable("listing_detail/{listingId}") { backStackEntry ->
            val idRumah = backStackEntry.arguments?.getString("listingId") ?: ""
            ListingDetailScreen(
                listingId = idRumah,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}