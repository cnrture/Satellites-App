package com.canerture.satellitesapp.domain.usecase.getsatellites

import kotlinx.coroutines.flow.Flow

interface GetSatellitesUseCase {
    operator fun invoke(): Flow<GetSatellitesUseCaseImpl.GetSatellitesUseCaseState>
}