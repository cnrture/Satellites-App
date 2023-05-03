package com.canerture.satellitesapp.detail

import com.canerture.satellitesapp.data.model.Position
import com.canerture.satellitesapp.data.model.SatelliteDetail

object DetailTestDataFactory {

    const val detailLoading = "Loading Detail"

    const val satelliteName = "Starship-1"

    val satelliteDetail = SatelliteDetail(
        id = 1,
        costPerLaunch = 7200000,
        firstFlight = "2021-12-01",
        height = 118,
        mass = 1167000
    )

    val position = Position(
        posX = 0.864328541,
        posY = 0.646450811
    )
}