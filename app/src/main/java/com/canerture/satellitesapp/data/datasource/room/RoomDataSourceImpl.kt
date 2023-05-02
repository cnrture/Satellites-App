package com.canerture.satellitesapp.data.datasource.room

import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.source.room.SatellitesDao

class RoomDataSourceImpl(
    private val satellitesDao: SatellitesDao
) : RoomDataSource {

    override suspend fun getSatelliteDetails(): List<SatelliteDetail>? =
        satellitesDao.getSatelliteDetails()

    override suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail) =
        satellitesDao.insertSatelliteDetail(satelliteDetail)
}