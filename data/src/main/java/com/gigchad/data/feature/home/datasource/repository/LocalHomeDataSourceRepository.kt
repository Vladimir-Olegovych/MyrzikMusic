package com.gigchad.data.feature.home.datasource.repository

import com.gigchad.domain.feature.home.models.MusicData

interface LocalHomeDataSourceRepository {
    suspend fun getAllFavorites(): List<MusicData>
    suspend fun updateFavorite(musicData: MusicData, value: Boolean)
}