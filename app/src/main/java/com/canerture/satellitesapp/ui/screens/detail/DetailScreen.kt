package com.canerture.satellitesapp.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.canerture.satellitesapp.ui.base.components.AlertDialog

@Composable
fun DetailRoute(
    isLoading: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val detailState = viewModel.state.value
    val detailEffect = viewModel.effect.value

    DetailScreen(
        state = detailState,
        effect = detailEffect,
        isLoading = isLoading,
        modifier = modifier
    )
}

@Composable
internal fun DetailScreen(
    state: DetailState,
    effect: DetailEffect,
    isLoading: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    isLoading(state.isLoading)

    when (effect) {
        DetailEffect.Idle -> Unit
        is DetailEffect.ShowError -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AlertDialog(
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
                Text(
                    text = state.satelliteName.orEmpty(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(4.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = it.firstFlight,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(4.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(12.dp))

                Row {
                    Text(
                        text = "Height/Mass:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(4.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${it.height}/${it.mass}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(4.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Row {
                    Text(
                        text = "Cost:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(4.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = it.costPerLaunch.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(4.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Row {
                    Text(
                        text = "Last Position:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(4.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "sdasadsad",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(4.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}