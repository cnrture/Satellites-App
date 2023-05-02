package com.canerture.satellitesapp.domain.usecase.searchsatellitesusecase

import com.canerture.satellitesapp.common.Resource
import com.canerture.satellitesapp.data.model.Satellite
import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchSatellitesUseCaseImpl @Inject constructor(
    private val satellitesRepository: SatellitesRepository
) : SearchSatellitesUseCase {

    override operator fun invoke(satelliteName: String): Flow<SearchSatellitesUseCaseState> = flow {
        satellitesRepository.searchSatellites(satelliteName).collect {
            when (it) {
                is Resource.Success -> {
                    if (it.data.isNullOrEmpty()) {
                        emit(SearchSatellitesUseCaseState.EmptyData)
                    } else {
                        emit(SearchSatellitesUseCaseState.Data(it.data))
                    }
                }

                is Resource.Error -> {
                    emit(SearchSatellitesUseCaseState.Error(it.message))
                }
            }
        }
    }

    sealed class SearchSatellitesUseCaseState {
        data class Data(val satellites: List<Satellite>) : SearchSatellitesUseCaseState()
        data class Error(val message: String) : SearchSatellitesUseCaseState()
        object EmptyData : SearchSatellitesUseCaseState()
    }
}