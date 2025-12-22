package com.gigchad.domain.feature.home.repository

import com.gigchad.domain.feature.home.models.MusicData

interface HomeRepository {
    suspend fun getPage(query: String, page: Int): Result<List<MusicData>>
}