package com.canerture.satellitesapp.data.datasource.json

import android.content.Context
import com.google.gson.Gson

class JsonDataSourceImpl(
    private val gson: Gson,
    private val context: Context
) : JsonDataSource {

}