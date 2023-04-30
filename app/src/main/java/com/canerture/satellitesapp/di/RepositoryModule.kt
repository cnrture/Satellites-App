package com.canerture.satellitesapp.di

import com.canerture.satellitesapp.data.datasource.json.JsonDataSource
import com.canerture.satellitesapp.data.datasource.room.RoomDataSource
import com.canerture.satellitesapp.data.repository.SatellitesRepositoryImpl
import com.canerture.satellitesapp.domain.repository.SatellitesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSatelliteRepository(
        jsonDataSource: JsonDataSource,
        roomDataSource: RoomDataSource
    ): SatellitesRepository = SatellitesRepositoryImpl(jsonDataSource, roomDataSource)
}