package com.gigchad.data.feature.home.di

import com.gigchad.data.feature.home.FavoriteRepositoryImpl
import com.gigchad.data.feature.home.HomeRepositoryImpl
import com.gigchad.data.feature.home.datasource.LocalFavoriteDataSourceImpl
import com.gigchad.data.feature.home.datasource.LocalHomeDataSourceImpl
import com.gigchad.data.feature.home.datasource.RemoteHomeDataSourceImpl
import com.gigchad.data.feature.home.datasource.repository.LocalFavoriteDataSourceRepository
import com.gigchad.data.feature.home.datasource.repository.LocalHomeDataSourceRepository
import com.gigchad.data.feature.home.datasource.repository.RemoteHomeDataSourceRepository
import com.gigchad.data.storage.room.dao.FavoriteDao
import com.gigchad.domain.feature.home.repository.FavoriteRepository
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
    fun provideRemoteHomeDataSourceRepository(): RemoteHomeDataSourceRepository {
        return RemoteHomeDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideLocalHomeDataSourceRepository(favoriteDao: FavoriteDao): LocalHomeDataSourceRepository {
        return LocalHomeDataSourceImpl(favoriteDao)
    }

    @Provides
    @Singleton
    fun provideLocalFavoriteDataSourceRepository(favoriteDao: FavoriteDao): LocalFavoriteDataSourceRepository {
        return LocalFavoriteDataSourceImpl(favoriteDao)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        localHomeDataSourceRepository: LocalHomeDataSourceRepository,
        remoteHomeDataSourceRepository: RemoteHomeDataSourceRepository
    ): HomeRepository {
        return HomeRepositoryImpl(
            localHomeDataSourceRepository = localHomeDataSourceRepository,
            remoteHomeDataSourceRepository = remoteHomeDataSourceRepository
        )
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favoriteDataSourceRepository: LocalFavoriteDataSourceRepository
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(
            favoriteDataSourceRepository = favoriteDataSourceRepository,
        )
    }
}