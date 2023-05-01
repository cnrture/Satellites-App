package com.canerture.satellitesapp.ui.screens.detail

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.canerture.satellitesapp.common.Constants.Route

fun NavController.navigateDetail(
    satellite: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        Route.detailNavigationRoute.plus("?satellite=${satellite}"),
        navOptions
    )
}

fun NavGraphBuilder.detailScreen() {
    composable(
        Route.detailNavigationRoute.plus("?satellite={satellite}"),
        content = {
            DetailRoute(
                viewModel = hiltViewModel()
            )
        }
    )
}