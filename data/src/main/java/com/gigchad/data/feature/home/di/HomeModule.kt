package com.gigchad.data.feature.home.di

import com.gigchad.data.feature.home.HomeRepositoryImpl
import com.gigchad.data.feature.home.datasource.HomeDataSourceRepositoryImpl
import com.gigchad.data.feature.home.datasource.repository.HomeDataSourceRepository
import com.gigchad.domain.feature.home.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {
    @Provides
    @Singleton
    fun provideHomeDataSourceRepository(): HomeDataSourceRepository {
        return HomeDataSourceRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        homeDataSourceRepository: HomeDataSourceRepository
    ): HomeRepository {
        return HomeRepositoryImpl(homeDataSourceRepository)
    }
}