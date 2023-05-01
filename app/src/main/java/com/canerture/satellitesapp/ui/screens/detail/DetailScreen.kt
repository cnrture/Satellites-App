package com.canerture.satellitesapp.ui.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        detailState,
        detailEffect,
        isLoading = isLoading,
        modifier
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.satellite?.let {

        }
    }
}