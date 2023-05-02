package com.canerture.satellitesapp.domain.repository

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Position
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.model.SatellitePosition
import kotlinx.coroutines.flow.Flow

interface SatellitesRepository {
    fun getSatellites(): Flow<Resource<List<Satellite>?>>
    fun getSatelliteDetail(satelliteId: Int): Flow<Resource<SatelliteDetail?>>
    fun getSatellitePosition(satelliteId: Int): Flow<Resource<Position?>>
}