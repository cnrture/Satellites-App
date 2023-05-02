package com.canerture.satellitesapp.domain.usecase.searchsatellitesusecase

import kotlinx.coroutines.flow.Flow

interface SearchSatellitesUseCase {
    operator fun invoke(satelliteName: String): Flow<SearchSatellitesUseCaseImpl.SearchSatellitesUseCaseState>
}