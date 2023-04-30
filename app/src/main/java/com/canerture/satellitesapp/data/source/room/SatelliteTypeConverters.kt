package com.canerture.satellitesapp.data.source.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.canerture.satellitesapp.data.model.Position
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

@ProvidedTypeConverter
class SatelliteTypeConverters @Inject constructor(private val gson: Gson) {

    @TypeConverter
    fun fromString(value: String?): List<Position?> {
        val listType = object : TypeToken<ArrayList<Position?>?>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Position?>?): String {
        return gson.toJson(list)
    }
}