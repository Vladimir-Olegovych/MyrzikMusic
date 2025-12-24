package com.gigchad.data.feature.home

import com.gigchad.data.feature.home.datasource.repository.LocalFavoriteDataSourceRepository
import com.gigchad.domain.feature.home.models.MusicData
import com.gigchad.domain.feature.home.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val favoriteDataSourceRepository: LocalFavoriteDataSourceRepository
): FavoriteRepository {
    override suspend fun getAllFavorites(): List<MusicData> {
        return favoriteDataSourceRepository.getAllFavorites()
    }

    override suspend fun updateFavorite(
        musicData: MusicData,
        value: Boolean
    ) {
        favoriteDataSourceRepository.updateFavorite(musicData, value)
    }
}