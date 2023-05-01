package com.canerture.satellitesapp.domain.usecase

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.SatelliteDetail
import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSatelliteDetailUseCase @Inject constructor(
    private val satellitesRepository: SatellitesRepository
) {
    operator fun invoke(satelliteId: Int): Flow<GetSatelliteDetailUseCaseState> = flow {

        when (val result = satellitesRepository.getSatelliteDetail(satelliteId)) {
            is Resource.Success -> {
                result.data?.let {
                    emit(GetSatelliteDetailUseCaseState.SatelliteDetailItem(it))
                } ?: kotlin.run {
                    emit(GetSatelliteDetailUseCaseState.EmptySatellite)
                }
            }

            is Resource.Error -> {
                emit(GetSatelliteDetailUseCaseState.Error(result.message))
            }
        }
    }

    sealed class GetSatelliteDetailUseCaseState {
        data class SatelliteDetailItem(val satelliteDetail: SatelliteDetail) :
            GetSatelliteDetailUseCaseState()

        data class Error(val message: String) : GetSatelliteDetailUseCaseState()
        object EmptySatellite : GetSatelliteDetailUseCaseState()
    }
}