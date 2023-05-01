package com.canerture.satellitesapp.ui.screens.satellites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.ui.base.components.AlertDialog
import com.canerture.satellitesapp.ui.base.components.SearchBar

@Composable
fun SatellitesRoute(
    onSatelliteClick: (Int) -> Unit,
    isLoading: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SatellitesViewModel = hiltViewModel()
) {
    val satellitesState = viewModel.state.value
    val satellitesEffect = viewModel.effect.value

    SatellitesScreen(
        satellitesState,
        satellitesEffect,
        isLoading = isLoading,
        onSatelliteClick = onSatelliteClick,
        modifier
    )
}

@Composable
internal fun SatellitesScreen(
    state: SatellitesState,
    effect: SatellitesEffect,
    isLoading: (Boolean) -> Unit,
    onSatelliteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    isLoading(state.isLoading)

    when (effect) {
        SatellitesEffect.Idle -> Unit
        is SatellitesEffect.ShowError -> {
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

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchBar(
            placeHolder = "Search",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
            keyboardActions = KeyboardActions(
                onSearch = { focusManager.clearFocus() }
            ),
        ) {

        }

        state.satellites?.let {
            LazyColumn(
                contentPadding = PaddingValues(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(it) { satelliteItem ->
                    SatelliteItem(
                        satellite = satelliteItem,
                        onSatelliteClick = onSatelliteClick
                    )
                }
            }
        }
    }
}

@Composable
fun SatelliteItem(
    satellite: Satellite,
    onSatelliteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth()
                .wrapContentHeight()
                .clickable { onSatelliteClick(satellite.id) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .background(
                        color = if (satellite.active) Color.Green else Color.Red,
                        shape = CircleShape
                    ),
                imageVector = if (satellite.active) Icons.Filled.Check else Icons.Filled.Close,
                tint = Color.White,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = satellite.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(4.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = if (satellite.active) "Active" else "Passive",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(4.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        Divider()
    }
}