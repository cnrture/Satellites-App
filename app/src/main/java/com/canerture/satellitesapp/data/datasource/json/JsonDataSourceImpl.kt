package com.canerture.satellitesapp.data.datasource.json

import android.content.Context
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
            val inputStream: InputStream = context.assets.open("satellite-list.json")
            val inputString = inputStream.bufferedReader().use { it.readText() }

            val satelliteList = mutableListOf<Satellite>()
            val jsonArray = JSONArray(inputString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                satelliteList.add(
                    Satellite(
                        id = jsonObject.optInt("id"),
                        active = jsonObject.optBoolean("active"),
                        name = jsonObject.optString("name")
                    )
                )
            }
            Resource.Success(satelliteList)
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }

    override suspend fun getSatelliteDetail(satelliteId: Int): Resource<SatelliteDetail?> =
        try {
            val inputStream: InputStream = context.assets.open("satellite-detail.json")
            val inputString = inputStream.bufferedReader().use { it.readText() }

            val satelliteDetailList = mutableListOf<SatelliteDetail>()
            val jsonArray = JSONArray(inputString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                satelliteDetailList.add(
                    SatelliteDetail(
                        id = jsonObject.optInt("id"),
                        costPerLaunch = jsonObject.optLong("cost_per_launch"),
                        firstFlight = jsonObject.optString("first_flight"),
                        height = jsonObject.optInt("height"),
                        mass = jsonObject.optLong("mass"),
                    )
                )
            }
            Resource.Success(satelliteDetailList.find { it.id == satelliteId })
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
}