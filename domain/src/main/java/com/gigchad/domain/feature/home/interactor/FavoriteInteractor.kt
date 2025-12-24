package com.gigchad.domain.feature.home.interactor

import com.gigchad.domain.feature.home.models.MusicData
import com.gigchad.domain.feature.home.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteInteractor @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun getAllFavorites(): List<MusicData> {
        return favoriteRepository.getAllFavorites()
    }
    suspend fun updateFavorite(musicData: MusicData, value: Boolean) {
        favoriteRepository.updateFavorite(musicData, value)
    }
}