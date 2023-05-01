package com.canerture.satellitesapp.ui.screens.satellites

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.canerture.satellitesapp.data.model.Satellite

const val satellitesNavigationRoute = "satellites_route"

fun NavGraphBuilder.satellitesScreen(
    isLoading: (Boolean) -> Unit,
    onSatelliteClick: (Satellite) -> Unit
) {
    composable(satellitesNavigationRoute) {
        SatellitesRoute(
            onSatelliteClick = onSatelliteClick,
            isLoading = isLoading
        )
    }
}