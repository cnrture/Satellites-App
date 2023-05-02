package com.canerture.satellitesapp.data.source.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.model.SatellitePosition

@Dao
interface SatellitesDao {

    @Query("SELECT * FROM satellite_detail")
    suspend fun getSatelliteDetails(): List<SatelliteDetail>

    @Query("SELECT * FROM satellite_positions")
    suspend fun getSatellitePositions(): List<SatellitePosition>

    @Insert
    suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail)

    @Insert
    suspend fun insertSatellitePosition(satellitePosition: SatellitePosition)

    @Delete
    suspend fun deleteSatelliteDetails(satelliteDetail: SatelliteDetail)

    @Delete
    suspend fun deleteSatellitePositions(satellitePosition: SatellitePosition)
}