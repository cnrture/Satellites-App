package com.canerture.satellitesapp.ui.screens.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.canerture.satellitesapp.ui.base.components.SatellitesAlertDialog
import com.canerture.satellitesapp.ui.base.components.SatellitesBoldText
import com.canerture.satellitesapp.ui.base.components.SatellitesHeadLineText
import com.canerture.satellitesapp.ui.base.components.SatellitesLightText
import com.canerture.satellitesapp.ui.base.components.SatellitesNormalText

@Composable
fun DetailRoute(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val detailState = viewModel.state.value
    val detailEffect = viewModel.effect.value

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
                    errorMessage = effect.message
                )
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        state.satelliteDetail?.let {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SatellitesHeadLineText(text = state.satelliteName.orEmpty())

                Spacer(modifier = Modifier.height(16.dp))

                SatellitesLightText(text = it.firstFlight.replace("-", "."))

                Spacer(modifier = Modifier.height(48.dp))

                Row {
                    SatellitesBoldText(text = "Height/Mass: ")
                    SatellitesNormalText(text = "${it.height}/${it.mass}")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    SatellitesBoldText(text = "Cost: ")
                    SatellitesNormalText(text = it.costPerLaunch.toString())
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    SatellitesBoldText(text = "Last Position: ")
                    AnimatedContent(
                        targetState = "(${state.position?.posX}, ${state.position?.posY})",
                        transitionSpec = {
                            slideInVertically { it } with slideOutVertically { -it }
                        }, label = ""
                    ) { char ->
                        SatellitesNormalText(text = char)
                    }
                }
            }
        }
    }
}