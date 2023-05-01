package com.canerture.satellitesapp.ui.screens.satellites

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.canerture.satellitesapp.common.Constants.Route
import com.canerture.satellitesapp.data.model.Satellite

fun NavGraphBuilder.satellitesScreen(
    onSatelliteClick: (Satellite) -> Unit
) {
    composable(Route.satellitesNavigationRoute) {
        SatellitesRoute(
            onSatelliteClick = onSatelliteClick
        )
    }
}