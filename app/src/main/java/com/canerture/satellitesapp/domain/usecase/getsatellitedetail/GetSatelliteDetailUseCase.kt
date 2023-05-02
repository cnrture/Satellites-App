package com.canerture.satellitesapp.domain.usecase.getsatellitedetail

import kotlinx.coroutines.flow.Flow

interface GetSatelliteDetailUseCase {
    operator fun invoke(satelliteId: Int): Flow<GetSatelliteDetailUseCaseImpl.GetSatelliteDetailUseCaseState>
}