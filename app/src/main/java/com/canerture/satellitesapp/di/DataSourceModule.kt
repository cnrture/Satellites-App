package com.canerture.satellitesapp.di

import android.content.Context
import androidx.room.Room
import com.canerture.satellitesapp.data.datasource.json.JsonDataSource
import com.canerture.satellitesapp.data.datasource.json.JsonDataSourceImpl
import com.canerture.satellitesapp.data.datasource.room.RoomDataSource
import com.canerture.satellitesapp.data.datasource.room.RoomDataSourceImpl
import com.canerture.satellitesapp.data.source.room.SatelliteTypeConverters
import com.canerture.satellitesapp.data.source.room.SatellitesDao
import com.canerture.satellitesapp.data.source.room.SatellitesDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideJsonDataSource(
        gson: Gson,
        @ApplicationContext context: Context
    ): JsonDataSource = JsonDataSourceImpl(gson, context)

    @Provides
    @Singleton
    fun provideRoomDataSource(satellitesDao: SatellitesDao): RoomDataSource =
        RoomDataSourceImpl(satellitesDao)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        typeConverters: SatelliteTypeConverters
    ): SatellitesDatabase = Room
        .databaseBuilder(context, SatellitesDatabase::class.java, "satellites.db")
        .addTypeConverter(typeConverters)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideSatelliteDao(satellitesDatabase: SatellitesDatabase): SatellitesDao =
        satellitesDatabase.getSatelliteDao()
}