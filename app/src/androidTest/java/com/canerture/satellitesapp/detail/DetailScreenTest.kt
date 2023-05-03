package com.canerture.satellitesapp.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.canerture.satellitesapp.R
import com.canerture.satellitesapp.detail.DetailTestDataFactory.detailLoading
import com.canerture.satellitesapp.detail.DetailTestDataFactory.position
import com.canerture.satellitesapp.detail.DetailTestDataFactory.satelliteDetail
import com.canerture.satellitesapp.detail.DetailTestDataFactory.satelliteName
import com.canerture.satellitesapp.ui.screens.detail.DetailEffect
import com.canerture.satellitesapp.ui.screens.detail.DetailScreen
import com.canerture.satellitesapp.ui.screens.detail.DetailState
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun getString(id: Int) = composeTestRule.activity.resources.getString(id)

    @Test
    fun progressBar_whenDetailScreenIsLoading_showLoading() {
        composeTestRule.setContent {
            DetailScreen(
                state = DetailState.Loading(true),
                effect = DetailEffect.Idle
            )
        }

        composeTestRule.onNodeWithContentDescription(detailLoading).assertExists()
    }

    @Test
    fun whenStateIsSatelliteDetailData_allDataAreDisplayed() {
        composeTestRule.setContent {
            DetailScreen(
                state = DetailState.SatelliteDetailData(
                    satelliteName = satelliteName,
                    satelliteDetail = satelliteDetail,
                    position = position
                ),
                effect = DetailEffect.Idle
            )
        }

        composeTestRule.onNodeWithText(satelliteName).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(satelliteDetail.firstFlight.replace("-", ".")).assertExists()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(getString(R.string.height_mass)).assertExists()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("${satelliteDetail.height}/${satelliteDetail.mass}")
            .assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(getString(R.string.cost)).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(satelliteDetail.costPerLaunch.toString()).assertExists()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(getString(R.string.last_position)).assertIsDisplayed()
        composeTestRule.onNodeWithText("(${position.posX}, ${position.posY})").assertExists()
            .assertIsDisplayed()
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
