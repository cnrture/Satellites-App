package com.canerture.satellitesapp.data.datasource.json

import android.content.Context
import com.canerture.satellitesapp.common.Constants.Json
import com.canerture.satellitesapp.common.getDataFromJsonFile
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.model.SatellitePosition
import com.canerture.satellitesapp.data.model.SatellitePositionsList

class JsonDataSourceImpl(
    private val context: Context
) : JsonDataSource {

    override suspend fun getSatellites(): List<Satellite> =
        context.getDataFromJsonFile<Array<Satellite>>(Json.SATELLITE_LIST_JSON).toList()

    override suspend fun getSatelliteDetail(satelliteId: Int): SatelliteDetail? =
        context.getDataFromJsonFile<Array<SatelliteDetail>>(Json.SATELLITE_DETAIL_JSON).toList()
            .find {
                it.id == satelliteId
            }

    override suspend fun getSatellitePositions(satelliteId: Int): SatellitePosition? =
        context.getDataFromJsonFile<SatellitePositionsList>(Json.SATELLITE_POSITIONS_JSON).list
            .find {
                it.id == satelliteId
            }
}