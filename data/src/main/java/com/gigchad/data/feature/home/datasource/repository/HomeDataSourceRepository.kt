package com.gigchad.data.feature.home.datasource.repository

import com.gigchad.domain.feature.home.models.MusicData

interface HomeDataSourceRepository {
    suspend fun getPage(query: String, page: Int): Result<List<MusicData>>
}