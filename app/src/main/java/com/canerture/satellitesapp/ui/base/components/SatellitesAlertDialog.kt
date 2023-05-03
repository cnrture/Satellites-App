package com.canerture.satellitesapp.ui.base.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.canerture.satellitesapp.R

@Composable
fun SatellitesAlertDialog(
    message: String,
    modifier: Modifier = Modifier
) {
    var dismissState by remember {
        mutableStateOf(true)
    }
    if (dismissState) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(align = Alignment.Center)
            ) {
                AlertDialog(
                    onDismissRequest = {
                        dismissState = false
                    },
                    text = {
                        Text(text = message)
                    },
                    confirmButton = {
                        Button(onClick = {
                            dismissState = false
                        }) {
                            Text(text = stringResource(R.string.okay))
                        }
                    }
                )
            }
        }
    }
}