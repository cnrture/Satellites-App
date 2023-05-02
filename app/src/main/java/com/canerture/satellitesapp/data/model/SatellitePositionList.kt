package com.canerture.satellitesapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SatellitePositionsList(
    val list: List<SatellitePosition>
)