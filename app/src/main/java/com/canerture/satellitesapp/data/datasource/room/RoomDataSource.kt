package com.canerture.satellitesapp.data.datasource.room

import com.canerture.satellitesapp.data.model.SatelliteDetail

interface RoomDataSource {
    suspend fun getSatelliteDetails(): List<SatelliteDetail>?
    suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail)
}