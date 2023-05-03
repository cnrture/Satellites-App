package com.canerture.satellitesapp.ui.base.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SatellitesBoldText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SatellitesSemiBoldText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SatellitesNormalText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Normal,
        color = color,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SatellitesHeadLineMediumText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SatellitesHeadLineSmallText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}