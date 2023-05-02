package com.canerture.satellitesapp.data.datasource.room

import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.model.SatellitePosition
import com.canerture.satellitesapp.data.source.room.SatellitesDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RoomDataSourceImpl(
    private val satellitesDao: SatellitesDao,
    private val ioDispatcher: CoroutineDispatcher
) : RoomDataSource {

    override suspend fun getSatelliteDetails() = withContext(ioDispatcher) {
        satellitesDao.getSatelliteDetails()
    }

    override suspend fun getSatellitePositions() = withContext(ioDispatcher) {
        satellitesDao.getSatellitePositions()
    }

    override suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail) =
        withContext(ioDispatcher) {
            satellitesDao.insertSatelliteDetail(satelliteDetail)
        }

    override suspend fun insertSatellitePosition(satellitePosition: SatellitePosition) =
        withContext(ioDispatcher) {
            satellitesDao.insertSatellitePosition(satellitePosition)
        }

    override suspend fun deleteSatelliteDetails(satelliteDetail: SatelliteDetail) =
        withContext(ioDispatcher) {
            satellitesDao.deleteSatelliteDetails(satelliteDetail)
        }

    override suspend fun deleteSatellitePositions(satellitePosition: SatellitePosition) =
        withContext(ioDispatcher) {
            satellitesDao.deleteSatellitePositions(satellitePosition)
        }
}