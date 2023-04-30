package com.canerture.satellitesapp.data.repository

import com.canerture.satellitesapp.data.datasource.json.JsonDataSource
import com.canerture.satellitesapp.data.datasource.room.RoomDataSource
import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import javax.inject.Inject

class SatellitesRepositoryImpl @Inject constructor(
    private val jsonDataSource: JsonDataSource,
    private val roomDataSource: RoomDataSource
) : SatellitesRepository {

}