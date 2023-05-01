package com.canerture.satellitesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.canerture.satellitesapp.ui.satellites.SatellitesRoute

const val satellitesNavigationRoute = "satellites_route"

@Composable
fun NavGraph(isLoading: (Boolean) -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    NavHost(
        navController = navController,
        startDestination = satellitesNavigationRoute
    ) {
        composable(satellitesNavigationRoute) {
            SatellitesRoute(
                onSatelliteClick = {

                },
                isLoading = {
                    isLoading(it)
                }
            )
        }
    }
}