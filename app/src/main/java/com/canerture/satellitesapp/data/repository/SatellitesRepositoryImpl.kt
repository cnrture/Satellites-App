package com.canerture.satellitesapp.data.repository

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.datasource.json.JsonDataSource
import com.canerture.satellitesapp.data.datasource.room.RoomDataSource
import com.canerture.satellitesapp.data.model.Position
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SatellitesRepositoryImpl @Inject constructor(
    private val jsonDataSource: JsonDataSource,
    private val roomDataSource: RoomDataSource
) : SatellitesRepository {

    private var job: Job? = null

    override fun getSatellites() = flow<Resource<List<Satellite>?>> {
        emit(Resource.Success(jsonDataSource.getSatellites()))
    }.catch {
        emit(Resource.Error(it.message.orEmpty()))
    }

    override fun searchSatellites(query: String) = flow<Resource<List<Satellite>?>> {
        emit(Resource.Success(jsonDataSource.searchSatellites(query)))
    }.catch {
        emit(Resource.Error(it.message.orEmpty()))
    }

    override fun getSatelliteDetail(satelliteId: Int) = flow<Resource<SatelliteDetail?>> {
        roomDataSource.getSatelliteDetails()?.find { it.id == satelliteId }?.let {
            emit(Resource.Success(it))
        } ?: kotlin.run {
            jsonDataSource.getSatelliteDetail(satelliteId)?.let {
                roomDataSource.deleteSatelliteDetails(it)
                roomDataSource.insertSatelliteDetail(it)
                emit(Resource.Success(it))
            }
        }
    }.catch {
        emit(Resource.Error(it.message.orEmpty()))
    }

    override fun getSatellitePosition(satelliteId: Int) = channelFlow<Resource<Position?>> {

        var index = 0
        val positionList = mutableListOf<Position>()
        job?.cancel()

        roomDataSource.getSatellitePositions()?.find { it.id == satelliteId }?.let {
            positionList.addAll(it.positions)
        } ?: kotlin.run {
            jsonDataSource.getSatellitePositions(satelliteId)?.let {
                roomDataSource.deleteSatellitePositions(it)
                roomDataSource.insertSatellitePosition(it)
                positionList.addAll(it.positions)
            }
        }

        job = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                trySend(Resource.Success(positionList.getOrNull(index)))
                if (index != 2) index++ else index = 0
                delay(3000)
            }
        }

        awaitClose {
            channel.close()
            job?.cancel()
        }
    }.catch {
        emit(Resource.Error(it.message.orEmpty()))
    }
}