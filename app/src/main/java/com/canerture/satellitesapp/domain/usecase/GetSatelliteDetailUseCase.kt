package com.canerture.satellitesapp.domain.usecase

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Position
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSatelliteDetailUseCase @Inject constructor(
    private val satellitesRepository: SatellitesRepository
) {
    operator fun invoke(satelliteId: Int): Flow<GetSatelliteDetailUseCaseState> = flow {

        val satelliteDetail = satellitesRepository.getSatelliteDetail(satelliteId)
        val satellitePositions = satellitesRepository.getSatellitePositions(satelliteId)

        satelliteDetail.combine(satellitePositions) { detail, positions ->

            when (detail) {
                is Resource.Success -> {

                    when (positions) {
                        is Resource.Success -> {
                            if (detail.data != null && positions.data?.positions != null) {
                                GetSatelliteDetailUseCaseState.SatelliteDetailItem(
                                    detail.data, positions.data.positions
                                )
                            } else {
                                GetSatelliteDetailUseCaseState.Error("positions.message")
                            }
                        }

                        is Resource.Error -> {
                            GetSatelliteDetailUseCaseState.Error(positions.message)
                        }
                    }
                }

                is Resource.Error -> {
                    GetSatelliteDetailUseCaseState.Error(detail.message)
                }
            }
        }.collect {
            emit(it)
        }
    }

    sealed class GetSatelliteDetailUseCaseState {
        data class SatelliteDetailItem(
            var satelliteDetail: SatelliteDetail,
            var position: List<Position>
        ) : GetSatelliteDetailUseCaseState()

        data class Error(val message: String) : GetSatelliteDetailUseCaseState()
        object EmptySatellite : GetSatelliteDetailUseCaseState()
    }
}