package com.canerture.satellitesapp.data.datasource.json

import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.model.SatellitePosition

interface JsonDataSource {
    suspend fun getSatellites(): List<Satellite>?
    suspend fun searchSatellites(query: String): List<Satellite>
    suspend fun getSatelliteDetail(satelliteId: Int): SatelliteDetail?
    suspend fun getSatellitePositions(satelliteId: Int): SatellitePosition?
}