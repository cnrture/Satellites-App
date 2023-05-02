package com.canerture.satellitesapp.domain.usecase.getsatellites

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSatellitesUseCaseImpl @Inject constructor(
    private val satellitesRepository: SatellitesRepository
) : GetSatellitesUseCase {

    override operator fun invoke(): Flow<GetSatellitesUseCaseState> = flow {
        satellitesRepository.getSatellites().collect {
            when (it) {
                is Resource.Success -> {
                    if (it.data.isNullOrEmpty()) {
                        emit(GetSatellitesUseCaseState.EmptyData)
                    } else {
                        emit(GetSatellitesUseCaseState.Data(it.data))
                    }
                }

                is Resource.Error -> {
                    emit(GetSatellitesUseCaseState.Error(it.message))
                }
            }
        }
    }

    sealed class GetSatellitesUseCaseState {
        data class Data(val satellites: List<Satellite>) : GetSatellitesUseCaseState()
        data class Error(val message: String) : GetSatellitesUseCaseState()
        object EmptyData : GetSatellitesUseCaseState()
    }
}