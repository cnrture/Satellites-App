package com.canerture.satellitesapp.domain.usecase.getsatellitedetail

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Position
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSatelliteDetailUseCaseImpl @Inject constructor(
    private val satellitesRepository: SatellitesRepository
) : GetSatelliteDetailUseCase {

    override operator fun invoke(satelliteId: Int): Flow<GetSatelliteDetailUseCaseState> = flow {

        val satelliteDetail = satellitesRepository.getSatelliteDetail(satelliteId)
        val satellitePositions = satellitesRepository.getSatellitePosition(satelliteId)

        satelliteDetail.combine(satellitePositions) { detail, positions ->

            when {
                detail is Resource.Error -> GetSatelliteDetailUseCaseState.Error(detail.message)
                positions is Resource.Error -> GetSatelliteDetailUseCaseState.Error(positions.message)
                detail is Resource.Success && positions is Resource.Success && detail.data != null && positions.data != null -> {
                    GetSatelliteDetailUseCaseState.Data(
                        detail.data, positions.data
                    )
                }

                else -> GetSatelliteDetailUseCaseState.EmptyData
            }
        }.collect {
            emit(it)
        }
    }

    sealed class GetSatelliteDetailUseCaseState {
        data class Data(
            val satelliteDetail: SatelliteDetail,
            val position: Position
        ) : GetSatelliteDetailUseCaseState()

        data class Error(val message: String) : GetSatelliteDetailUseCaseState()
        object EmptyData : GetSatelliteDetailUseCaseState()
    }
}