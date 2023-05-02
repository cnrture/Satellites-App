package com.canerture.satellitesapp.data.source.local

import java.io.InputStream

fun interface AssetManager {
    fun open(fileName: String): InputStream
}