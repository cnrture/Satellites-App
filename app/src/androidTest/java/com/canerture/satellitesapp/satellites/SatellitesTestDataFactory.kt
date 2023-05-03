package com.canerture.satellitesapp.satellites

import com.canerture.satellitesapp.data.model.Satellite

object SatellitesTestDataFactory {

    const val satellitesLoading = "Loading Satellites"

    val satelliteList = listOf(
        Satellite(
            id = 1,
            active = false,
            name = "Starship-1"
        ),
        Satellite(
            id = 2,
            active = false,
            name = "Dragon-1"
        ),
        Satellite(
            id = 3,
            active = false,
            name = "Starship-3"
        )
    )
}