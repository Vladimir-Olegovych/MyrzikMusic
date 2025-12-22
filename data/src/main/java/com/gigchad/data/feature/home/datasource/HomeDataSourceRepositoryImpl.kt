package com.gigchad.data.feature.home.datasource

import com.gigchad.data.feature.home.datasource.repository.HomeDataSourceRepository
import com.gigchad.domain.feature.home.models.MusicData

class HomeDataSourceRepositoryImpl: HomeDataSourceRepository {
    override suspend fun getMusic(): Result<List<MusicData>> {
        return Result.success(
            listOf(
                MusicData("bla bla", ""),
                MusicData("bla bla2", ""),
                MusicData("bla bla3", ""),
                MusicData("bla bla4", ""),
            )
        )
    }
}