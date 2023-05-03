package com.canerture.satellitesapp.ui.screens.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.canerture.satellitesapp.R
import com.canerture.satellitesapp.ui.base.components.EmptyDataScreen
import com.canerture.satellitesapp.ui.base.components.SatellitesAlertDialog
import com.canerture.satellitesapp.ui.base.components.SatellitesBoldText
import com.canerture.satellitesapp.ui.base.components.SatellitesHeadLineMediumText
import com.canerture.satellitesapp.ui.base.components.SatellitesLightText
import com.canerture.satellitesapp.ui.base.components.SatellitesNormalText
import com.canerture.satellitesapp.ui.base.components.SatellitesProgressBar

@Composable
fun DetailRoute(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val detailState by viewModel.state.collectAsState()
    val detailEffect by viewModel.effect.collectAsState(DetailEffect.Idle)

    DetailScreen(
        state = detailState,
        effect = detailEffect,
        modifier = modifier
    )
}

@Composable
fun DetailScreen(
    state: DetailState,
    effect: DetailEffect,
    modifier: Modifier = Modifier
) {

    when (effect) {
        DetailEffect.Idle -> Unit
        is DetailEffect.ShowError -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SatellitesAlertDialog(
                    message = effect.message
                )
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when (state) {
            is DetailState.Loading ->
                if (state.isLoading) SatellitesProgressBar(
                    contentDesc = stringResource(R.string.loading_detail)
                )

            is DetailState.SatelliteDetailData -> {
                SatellitesHeadLineMediumText(text = state.satelliteName)

                Spacer(modifier = Modifier.height(16.dp))

                SatellitesLightText(text = state.satelliteDetail.firstFlight.replace("-", "."))

                Spacer(modifier = Modifier.height(48.dp))

                Row {
                    SatellitesBoldText(text = stringResource(R.string.height_mass))
                    SatellitesNormalText(text = "${state.satelliteDetail.height}/${state.satelliteDetail.mass}")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    SatellitesBoldText(text = stringResource(R.string.cost))
                    SatellitesNormalText(text = state.satelliteDetail.costPerLaunch.toString())
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    SatellitesBoldText(text = stringResource(R.string.last_position))
                    AnimatedContent(
                        targetState = "(${state.position.posX}, ${state.position.posY})",
                        transitionSpec = {
                            slideInVertically { it } with slideOutVertically { -it }
                        }, label = ""
                    ) { char ->
                        SatellitesNormalText(text = char)
                    }
                }
            }

            DetailState.EmptyData -> EmptyDataScreen()
        }
    }
}