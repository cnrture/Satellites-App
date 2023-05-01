package com.canerture.satellitesapp.domain.usecase

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSatellitesUseCase @Inject constructor(
    private val satellitesRepository: SatellitesRepository
) {
    operator fun invoke(): Flow<GetSatellitesUseCaseState> = flow {

        when (val result = satellitesRepository.getSatellites()) {
            is Resource.Success -> {
                if (result.data.isNullOrEmpty()) {
                    emit(GetSatellitesUseCaseState.EmptyList)
                } else {
                    emit(GetSatellitesUseCaseState.Satellites(result.data))
                }
            }

            is Resource.Error -> {
                emit(GetSatellitesUseCaseState.Error(result.message))
            }
        }
    }

    sealed class GetSatellitesUseCaseState {
        data class Satellites(val satellites: List<Satellite>) : GetSatellitesUseCaseState()
        data class Error(val message: String) : GetSatellitesUseCaseState()
        object EmptyList : GetSatellitesUseCaseState()
    }
}