package com.canerture.satellitesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.canerture.satellitesapp.common.Constants.Route
import com.canerture.satellitesapp.common.toJson
import com.canerture.satellitesapp.ui.screens.detail.detailScreen
import com.canerture.satellitesapp.ui.screens.detail.navigateDetail
import com.canerture.satellitesapp.ui.screens.satellites.satellitesScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    NavHost(
        navController = navController,
        startDestination = Route.satellitesNavigationRoute
    ) {
        satellitesScreen(
            onSatelliteClick = { navController.navigateDetail(it.toJson()) }
        )
        detailScreen()
    }
}