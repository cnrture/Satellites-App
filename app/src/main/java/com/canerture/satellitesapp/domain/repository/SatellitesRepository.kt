package com.canerture.satellitesapp.domain.repository

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.model.SatellitePosition
import kotlinx.coroutines.flow.Flow

interface SatellitesRepository {
    suspend fun getSatellites(): Resource<List<Satellite>?>
    suspend fun getSatelliteDetail(satelliteId: Int): Flow<Resource<SatelliteDetail?>>
    suspend fun getSatellitePositions(satelliteId: Int): Flow<Resource<SatellitePosition?>>
}