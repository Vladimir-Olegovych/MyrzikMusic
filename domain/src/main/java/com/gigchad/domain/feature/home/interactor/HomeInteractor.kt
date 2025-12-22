package com.gigchad.domain.feature.home.interactor

import com.gigchad.domain.feature.home.models.MusicData
import com.gigchad.domain.feature.home.repository.HomeRepository
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend fun getMusic(): Result<List<MusicData>> {
        return homeRepository.getMusic()
    }
}