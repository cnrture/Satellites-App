package com.canerture.satellitesapp.common

import com.google.gson.Gson

inline fun <reified T> T.toJson(): String {
    return try {
        Gson().toJson(this)
    } catch (ex: Exception) {
        ""
    }
}