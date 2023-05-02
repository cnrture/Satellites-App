package com.canerture.satellitesapp.di

import android.app.Application
import com.canerture.satellitesapp.infrastructure.StringResourceProvider
import com.canerture.satellitesapp.infrastructure.StringResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InfrastructureModule {

    @Provides
    @Singleton
    fun provideStringResourceProvider(application: Application): StringResourceProvider {
        return StringResourceProviderImpl(application)
    }
}