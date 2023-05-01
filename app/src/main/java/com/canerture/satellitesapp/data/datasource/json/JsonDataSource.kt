package com.canerture.satellitesapp.data.datasource.json

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Satellite

interface JsonDataSource {
    suspend fun getSatellites(): Resource<List<Satellite>>
}