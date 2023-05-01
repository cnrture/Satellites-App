package com.canerture.satellitesapp.data.datasource.json

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail

interface JsonDataSource {
    suspend fun getSatellites(): Resource<List<Satellite>?>
    suspend fun getSatelliteDetail(satelliteId: Int): Resource<SatelliteDetail?>
}