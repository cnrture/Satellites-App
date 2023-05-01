package com.canerture.satellitesapp.data.datasource.json

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.model.SatellitePosition

interface JsonDataSource {
    suspend fun getSatellites(): Resource<List<Satellite>?>
    suspend fun getSatelliteDetail(satelliteId: Int): Resource<SatelliteDetail?>
    suspend fun getSatellitePositions(satelliteId: Int): Resource<SatellitePosition?>
}