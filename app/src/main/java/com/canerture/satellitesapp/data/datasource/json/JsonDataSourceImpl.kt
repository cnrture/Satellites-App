package com.canerture.satellitesapp.data.datasource.json

import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.data.model.SatellitePosition
import com.canerture.satellitesapp.data.model.SatellitePositionsList
import com.canerture.satellitesapp.data.source.local.AssetManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream

@OptIn(ExperimentalSerializationApi::class)
class JsonDataSourceImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val assetManager: AssetManager,
    private val json: Json
) : JsonDataSource {

    override suspend fun getSatellites(): List<Satellite> = withContext(ioDispatcher) {
        assetManager.open(SATELLITE_LIST_JSON).use(json::decodeFromStream)
    }

    override suspend fun searchSatellites(query: String): List<Satellite> =
        withContext(ioDispatcher) {
            assetManager.open(SATELLITE_LIST_JSON)
                .use<InputStream, List<Satellite>>(json::decodeFromStream)
                .filter {
                    it.name.lowercase().contains(query.lowercase())
                }
        }

    override suspend fun getSatelliteDetail(satelliteId: Int): SatelliteDetail? =
        withContext(ioDispatcher) {
            assetManager.open(SATELLITE_DETAIL_JSON)
                .use<InputStream, Array<SatelliteDetail>>(json::decodeFromStream)
                .find {
                    it.id == satelliteId
                }
        }

    override suspend fun getSatellitePositions(satelliteId: Int): SatellitePosition? =
        withContext(ioDispatcher) {
            assetManager.open(SATELLITE_POSITIONS_JSON)
                .use<InputStream, SatellitePositionsList>(json::decodeFromStream).list
                .find {
                    it.id == satelliteId
                }
        }

    companion object {
        private const val SATELLITE_LIST_JSON = "satellite-list.json"
        private const val SATELLITE_DETAIL_JSON = "satellite-detail.json"
        private const val SATELLITE_POSITIONS_JSON = "positions.json"
    }
}