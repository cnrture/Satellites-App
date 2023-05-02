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
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.canerture.satellitesapp.R
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.ui.base.components.SatellitesAlertDialog
import com.canerture.satellitesapp.ui.base.components.SatellitesNormalText
import com.canerture.satellitesapp.ui.base.components.SatellitesProgressBar
import com.canerture.satellitesapp.ui.base.components.SatellitesSearchBar
import com.canerture.satellitesapp.ui.base.components.SatellitesSemiBoldText

@Composable
fun SatellitesRoute(
    onSatelliteClick: (Satellite) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SatellitesViewModel = hiltViewModel()
) {
    val satellitesState = viewModel.state.value
    val satellitesEffect = viewModel.effect.value

    SatellitesScreen(
        state = satellitesState,
        effect = satellitesEffect,
        onSatelliteClick = onSatelliteClick,
        onQueryTextChange = {
            viewModel.onQueryTextChange(it)
        },
        modifier = modifier
    )
}

@Composable
fun SatellitesScreen(
    state: SatellitesState,
    effect: SatellitesEffect,
    onSatelliteClick: (Satellite) -> Unit,
    onQueryTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    when (effect) {
        SatellitesEffect.Idle -> Unit
        is SatellitesEffect.ShowError -> {
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

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SatellitesSearchBar(
            placeHolder = stringResource(R.string.search),
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
            keyboardActions = KeyboardActions(
                onSearch = { focusManager.clearFocus() }
            ),
        ) { query ->
            onQueryTextChange(query)
        }

        SatellitesProgressBar(isVisible = state.isLoading)

        state.satellites?.let {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(it) { satelliteItem ->
                    SatelliteItem(
                        satellite = satelliteItem,
                        isLastItem = satelliteItem.id == it.size,
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
    isLastItem: Boolean,
    onSatelliteClick: (Satellite) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable { onSatelliteClick(satellite) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .background(
                        color = if (satellite.active) Color.Green else Color.Red,
                        shape = CircleShape
                    )
                    .wrapContentWidth(),
                imageVector = if (satellite.active) Icons.Filled.Check else Icons.Filled.Close,
                tint = Color.White,
                contentDescription = stringResource(R.string.active_passive_icon_desc)
            )

            Spacer(modifier = Modifier.width(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(0.3f)
            ) {
                SatellitesSemiBoldText(text = satellite.name)

                Spacer(modifier = Modifier.height(2.dp))

                SatellitesNormalText(
                    text = stringResource(if (satellite.active) R.string.active else R.string.passive)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (!isLastItem) Divider()

        Spacer(modifier = Modifier.height(8.dp))
    }
}