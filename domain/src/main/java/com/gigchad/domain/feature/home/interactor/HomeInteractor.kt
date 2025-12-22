package com.gigchad.domain.feature.home.interactor

import com.gigchad.domain.feature.home.models.MusicData
import com.gigchad.domain.feature.home.repository.HomeRepository
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend fun getPage(query: String, page: Int): Result<List<MusicData>> {
        return homeRepository.getPage(query = query, page = page)
    }
}