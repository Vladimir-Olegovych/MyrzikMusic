package com.gigchad.data.feature.home.datasource

import com.gigchad.data.feature.home.datasource.repository.LocalHomeDataSourceRepository
import com.gigchad.data.storage.room.dao.FavoriteDao
import com.gigchad.data.storage.room.entity.toFavoriteEntity
import com.gigchad.data.storage.room.entity.toMusicData
import com.gigchad.domain.feature.home.models.MusicData

class LocalHomeDataSourceImpl(
    private val favoriteDao: FavoriteDao
): LocalHomeDataSourceRepository {
    override suspend fun getAllFavorites(): List<MusicData> {
        return try {
            favoriteDao.getAll().map { it.toMusicData() }
        } catch (_: Throwable) {
            emptyList()
        }
    }

    override suspend fun updateFavorite(
        musicData: MusicData,
        value: Boolean
    ) {
        if (value){
            favoriteDao.insertOrUpdate(musicData.toFavoriteEntity())
        } else {
            favoriteDao.delete(musicData.toFavoriteEntity())
        }
    }
}