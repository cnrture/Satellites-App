package com.canerture.satellitesapp.data.datasource.json

import android.content.Context
import com.canerture.satellitesapp.common.Constants.Json
import com.canerture.satellitesapp.common.Constants.Name
import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import org.json.JSONArray
import java.io.InputStream

class JsonDataSourceImpl(
    private val context: Context
) : JsonDataSource {

    override suspend fun getSatellites(): Resource<List<Satellite>> =
        try {
            val inputStream: InputStream = context.assets.open(Json.SATELLITE_LIST_JSON)
            val inputString = inputStream.bufferedReader().use { it.readText() }

            val satelliteList = mutableListOf<Satellite>()
            val jsonArray = JSONArray(inputString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                satelliteList.add(
                    Satellite(
                        id = jsonObject.optInt(Name.ID),
                        active = jsonObject.optBoolean(Name.ACTIVE),
                        name = jsonObject.optString(Name.NAME)
                    )
                )
            }
            Resource.Success(satelliteList)
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }

    override suspend fun getSatelliteDetail(satelliteId: Int): Resource<SatelliteDetail?> =
        try {
            val inputStream: InputStream = context.assets.open(Json.SATELLITE_DETAIL_JSON)
            val inputString = inputStream.bufferedReader().use { it.readText() }

            val satelliteDetailList = mutableListOf<SatelliteDetail>()
            val jsonArray = JSONArray(inputString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                satelliteDetailList.add(
                    SatelliteDetail(
                        id = jsonObject.optInt(Name.ID),
                        costPerLaunch = jsonObject.optLong(Name.COST_PER_LAUNCH),
                        firstFlight = jsonObject.optString(Name.FIRST_FLIGHT),
                        height = jsonObject.optInt(Name.HEIGHT),
                        mass = jsonObject.optLong(Name.MASS),
                    )
                )
            }
            Resource.Success(satelliteDetailList.find { it.id == satelliteId })
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
}