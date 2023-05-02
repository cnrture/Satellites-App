package com.canerture.satellitesapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Position(
    val posX: Double,
    val posY: Double
)