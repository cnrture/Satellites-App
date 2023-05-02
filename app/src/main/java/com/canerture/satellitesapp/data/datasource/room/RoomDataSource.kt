package com.canerture.satellitesapp.data.datasource.room

import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.model.SatellitePosition

interface RoomDataSource {
    suspend fun getSatelliteDetails(): List<SatelliteDetail>?
    suspend fun getSatellitePositions(): List<SatellitePosition>?
    suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail)
    suspend fun insertSatellitePosition(satellitePosition: SatellitePosition)
    suspend fun deleteSatelliteDetails(satelliteDetail: SatelliteDetail)
    suspend fun deleteSatellitePositions(satellitePosition: SatellitePosition)
}