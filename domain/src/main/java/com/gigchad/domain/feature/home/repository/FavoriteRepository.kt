package com.gigchad.domain.feature.home.repository

import com.gigchad.domain.feature.home.models.MusicData

interface FavoriteRepository {
    suspend fun getAllFavorites(): List<MusicData>
    suspend fun updateFavorite(musicData: MusicData, value: Boolean)
}