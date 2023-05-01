package com.canerture.satellitesapp.domain.repository

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Satellite

interface SatellitesRepository {
    suspend fun getSatellites(): Resource<List<Satellite>>
}