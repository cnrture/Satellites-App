package com.canerture.satellitesapp.ui.screens.satellites

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val satellitesNavigationRoute = "satellites_route"

fun NavGraphBuilder.satellitesScreen(
    isLoading: (Boolean) -> Unit,
    onSatelliteClick: (Int) -> Unit
) {
    composable(satellitesNavigationRoute) {
        SatellitesRoute(
            onSatelliteClick = onSatelliteClick,
            isLoading = isLoading
        )
    }
}