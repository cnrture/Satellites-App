package com.canerture.satellitesapp.data.repository

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.datasource.json.JsonDataSource
import com.canerture.satellitesapp.data.datasource.room.RoomDataSource
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import javax.inject.Inject

class SatellitesRepositoryImpl @Inject constructor(
    private val jsonDataSource: JsonDataSource,
    private val roomDataSource: RoomDataSource
) : SatellitesRepository {

    override suspend fun getSatellites() = jsonDataSource.getSatellites()
    override suspend fun getSatelliteDetail(satelliteId: Int) = jsonDataSource.getSatelliteDetail(satelliteId)
}