package com.canerture.satellitesapp.ui.screens.detail

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val detailNavigationRoute = "detail_route"

fun NavController.navigateDetail(
    satellite: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        detailNavigationRoute.plus("?satellite=${satellite}"),
        navOptions
    )
}

fun NavGraphBuilder.detailScreen(isLoading: (Boolean) -> Unit) {
    composable(
        detailNavigationRoute.plus("?satellite={satellite}"),
        content = {
            DetailRoute(
                isLoading = isLoading,
                viewModel = hiltViewModel()
            )
        }
    )
}