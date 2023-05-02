package com.canerture.satellitesapp.di

import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import com.canerture.satellitesapp.domain.usecase.getsatellitedetail.GetSatelliteDetailUseCase
import com.canerture.satellitesapp.domain.usecase.getsatellitedetail.GetSatelliteDetailUseCaseImpl
import com.canerture.satellitesapp.domain.usecase.getsatellites.GetSatellitesUseCase
import com.canerture.satellitesapp.domain.usecase.getsatellites.GetSatellitesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

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
}