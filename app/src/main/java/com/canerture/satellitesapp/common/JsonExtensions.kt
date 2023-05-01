package com.canerture.satellitesapp.common

import android.content.Context
import com.canerture.satellitesapp.data.model.Satellite
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.StringReader

inline fun <reified T> T.toJson(): String {
    return try {
        Gson().toJson(this)
    } catch (ex: Exception) {
        ""
    }
}

inline fun <reified T : Any> Context.getDataFromJsonFile(file: String): T =
    GsonBuilder().serializeNulls().create().fromJson(
        StringReader(assets.open(file).bufferedReader().use { it.readText() }), T::class.java
    )

inline fun <reified T : Any> getDataFromJsonString(jsonString: String): T? =
    try {
        Gson().fromJson(jsonString, T::class.java)
    } catch (e: Exception) {
        null
    }