package com.canerture.satellitesapp.di

import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import com.canerture.satellitesapp.domain.usecase.getsatellitedetail.GetSatelliteDetailUseCase
import com.canerture.satellitesapp.domain.usecase.getsatellitedetail.GetSatelliteDetailUseCaseImpl
import com.canerture.satellitesapp.domain.usecase.getsatellites.GetSatellitesUseCase
import com.canerture.satellitesapp.domain.usecase.getsatellites.GetSatellitesUseCaseImpl
import com.canerture.satellitesapp.domain.usecase.searchsatellitesusecase.SearchSatellitesUseCase
import com.canerture.satellitesapp.domain.usecase.searchsatellitesusecase.SearchSatellitesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetSatellitesUseCase(
        satellitesRepository: SatellitesRepository
    ): GetSatellitesUseCase = GetSatellitesUseCaseImpl(satellitesRepository)

    @Provides
    @ViewModelScoped
    fun provideGetSatelliteDetailUseCase(
        satellitesRepository: SatellitesRepository
    ): GetSatelliteDetailUseCase = GetSatelliteDetailUseCaseImpl(satellitesRepository)

    @Provides
    @ViewModelScoped
    fun provideSearchSatellitesUseCase(
        satellitesRepository: SatellitesRepository
    ): SearchSatellitesUseCase = SearchSatellitesUseCaseImpl(satellitesRepository)
}