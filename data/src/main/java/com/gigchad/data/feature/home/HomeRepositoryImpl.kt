package com.gigchad.data.feature.home

import com.gigchad.data.feature.home.datasource.repository.HomeDataSourceRepository
import com.gigchad.domain.feature.home.models.MusicData
import com.gigchad.domain.feature.home.repository.HomeRepository

class HomeRepositoryImpl(
    private val homeDataSourceRepository: HomeDataSourceRepository
): HomeRepository {
    override suspend fun getPage(query: String, page: Int): Result<List<MusicData>> {
         return homeDataSourceRepository.getPage(query = query, page = page)
    }
}