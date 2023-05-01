package com.canerture.satellitesapp.data.datasource.json

import android.content.Context
import com.canerture.satellitesapp.common.Constants.Json
import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.common.getDataFromJsonFile
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.model.SatellitePosition
import com.canerture.satellitesapp.data.model.SatellitePositionsList

class JsonDataSourceImpl(
    private val context: Context
) : JsonDataSource {

    override suspend fun getSatellites(): Resource<List<Satellite>> =
        try {
            Resource.Success(
                context.getDataFromJsonFile<Array<Satellite>>(Json.SATELLITE_LIST_JSON).toList()
            )
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }

    override suspend fun getSatelliteDetail(satelliteId: Int): Resource<SatelliteDetail?> =
        try {
            Resource.Success(
                context.getDataFromJsonFile<Array<SatelliteDetail>>(Json.SATELLITE_DETAIL_JSON)
                    .toList()
                    .find {
                        it.id == satelliteId
                    }
            )
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }

    override suspend fun getSatellitePositions(satelliteId: Int): Resource<SatellitePosition?> =
        try {
            Resource.Success(
                context.getDataFromJsonFile<SatellitePositionsList>(Json.SATELLITE_POSITIONS_JSON).list.find {
                    it.id == satelliteId
                }
            )
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
}