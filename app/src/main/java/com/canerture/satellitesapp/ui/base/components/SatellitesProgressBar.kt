package com.canerture.satellitesapp.ui.base.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.canerture.satellitesapp.R

@Composable
fun SatellitesProgressBar(
    contentDesc: String,
    modifier: Modifier = Modifier
) {
    val progressValue = 1f
    val infiniteTransition = rememberInfiniteTransition(
        label = stringResource(R.string.progress_bar_transition)
    )

    val progressAnimationValue by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = progressValue,
        animationSpec = infiniteRepeatable(animation = tween(900)),
        label = stringResource(R.string.progress_bar_animation)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .semantics { contentDescription = contentDesc },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = progressAnimationValue,
            modifier = Modifier
                .wrapContentSize(),
            color = Color.Red.copy(alpha = 0.8f)
        )
    }
}