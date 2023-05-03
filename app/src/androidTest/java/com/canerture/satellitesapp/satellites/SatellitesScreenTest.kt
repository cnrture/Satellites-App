package com.canerture.satellitesapp.satellites

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import com.canerture.satellitesapp.R
import com.canerture.satellitesapp.satellites.SatellitesTestDataFactory.satelliteList
import com.canerture.satellitesapp.satellites.SatellitesTestDataFactory.satellitesLoading
import com.canerture.satellitesapp.ui.screens.detail.DetailEffect
import com.canerture.satellitesapp.ui.screens.detail.DetailScreen
import com.canerture.satellitesapp.ui.screens.detail.DetailState
import com.canerture.satellitesapp.ui.screens.satellites.SatellitesEffect
import com.canerture.satellitesapp.ui.screens.satellites.SatellitesScreen
import com.canerture.satellitesapp.ui.screens.satellites.SatellitesState
import org.junit.Rule
import org.junit.Test

class SatellitesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun getString(id: Int) = composeTestRule.activity.resources.getString(id)

    @Test
    fun progressBar_whenSatellitesScreenIsLoading_showLoading() {
        composeTestRule.setContent {
            SatellitesScreen(
                state = SatellitesState.Loading(true),
                effect = SatellitesEffect.Idle,
                onSatelliteClick = {},
                onQueryTextChange = {}
            )
        }

        composeTestRule.onNodeWithContentDescription(satellitesLoading)
            .assertExists()
    }

    @Test
    fun searchTextField_isFocused() {
        composeTestRule.setContent {
            SatellitesScreen(
                state = SatellitesState.SatellitesData(satelliteList),
                effect = SatellitesEffect.Idle,
                onSatelliteClick = {},
                onQueryTextChange = {}
            )
        }

        composeTestRule
            .onNodeWithText(getString(R.string.search))
            .performClick()
            .assertIsFocused()
    }

    @Test
    fun searchTextField_isFilled() {
        composeTestRule.setContent {
            SatellitesScreen(
                state = SatellitesState.SatellitesData(listOf(satelliteList[1])),
                effect = SatellitesEffect.Idle,
                onSatelliteClick = {},
                onQueryTextChange = {}
            )
        }

        composeTestRule
            .onNodeWithText(getString(R.string.search))
            .performTextInput("dra")

        composeTestRule
            .onNodeWithText("dra")
            .assertExists()

        composeTestRule
            .onNodeWithText(
                satelliteList[1].name,
                substring = true,
            )
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun satellitesLazyColumn_whenHasData_showsSatellites() {
        composeTestRule.setContent {
            SatellitesScreen(
                state = SatellitesState.SatellitesData(satelliteList),
                effect = SatellitesEffect.Idle,
                onSatelliteClick = {},
                onQueryTextChange = {},
            )
        }

        composeTestRule
            .onNodeWithText(
                satelliteList[0].name,
                substring = true,
            )
            .assertExists()
            .assertHasClickAction()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    satelliteList[1].name,
                    substring = true,
                ),
            )

        composeTestRule
            .onNodeWithText(
                satelliteList[1].name,
                substring = true,
            )
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun whenStateIsEmptyData_emptyDataScreenDisplayed() {
        composeTestRule.setContent {
            DetailScreen(
                state = DetailState.EmptyData,
                effect = DetailEffect.Idle
            )
        }

        composeTestRule.onNodeWithText(getString(R.string.something_went_wrong)).assertExists()
    }
}