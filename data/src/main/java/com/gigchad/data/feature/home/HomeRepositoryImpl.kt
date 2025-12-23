package com.gigchad.data.feature.home

import com.gigchad.data.feature.home.datasource.repository.LocalHomeDataSourceRepository
import com.gigchad.data.feature.home.datasource.repository.RemoteHomeDataSourceRepository
import com.gigchad.domain.feature.home.models.MusicData
import com.gigchad.domain.feature.home.repository.HomeRepository

class HomeRepositoryImpl(
    private val localHomeDataSourceRepository: LocalHomeDataSourceRepository,
    private val remoteHomeDataSourceRepository: RemoteHomeDataSourceRepository
): HomeRepository {

    override suspend fun getAllFavorites(): List<MusicData> {
        return localHomeDataSourceRepository.getAllFavorites()
    }

    override suspend fun updateFavorite(
        musicData: MusicData,
        value: Boolean
    ) {
        return localHomeDataSourceRepository.updateFavorite(musicData, value)
    }

    override suspend fun getPage(query: String, page: Int): Result<List<MusicData>> {
         return remoteHomeDataSourceRepository.getPage(query = query, page = page)
    }
}