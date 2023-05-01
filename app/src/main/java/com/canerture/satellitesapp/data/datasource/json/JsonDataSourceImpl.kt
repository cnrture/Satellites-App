package com.canerture.satellitesapp.data.datasource.json

import android.content.Context
import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Satellite
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
}