package com.gigchad.data.feature.home.datasource.repository

import com.gigchad.domain.feature.home.models.MusicData

interface RemoteHomeDataSourceRepository {
    suspend fun getPage(query: String, page: Int): Result<List<MusicData>>
}