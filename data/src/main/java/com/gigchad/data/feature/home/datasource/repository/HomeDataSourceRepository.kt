package com.gigchad.data.feature.home.datasource.repository

import com.gigchad.domain.feature.home.models.MusicData

interface HomeDataSourceRepository {
    suspend fun getMusic(): Result<List<MusicData>>
}