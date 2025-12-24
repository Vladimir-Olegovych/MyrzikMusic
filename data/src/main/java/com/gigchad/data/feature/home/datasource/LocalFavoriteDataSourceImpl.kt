package com.gigchad.data.feature.home.datasource

import com.gigchad.data.feature.home.datasource.repository.LocalFavoriteDataSourceRepository
import com.gigchad.data.feature.home.datasource.repository.LocalHomeDataSourceRepository
import com.gigchad.data.storage.room.dao.FavoriteDao
import com.gigchad.data.storage.room.entity.toFavoriteEntity
import com.gigchad.data.storage.room.entity.toMusicData
import com.gigchad.domain.feature.home.models.MusicData

class LocalFavoriteDataSourceImpl(
    private val favoriteDao: FavoriteDao
): LocalFavoriteDataSourceRepository {
    override suspend fun getAllFavorites(): List<MusicData> {
        return try {
            favoriteDao.getAll().map { it.toMusicData() }.apply {
                this.forEach {                println(it)
                }
            }
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
            favoriteDao.deleteByServerId(musicData.serverId)
        }
    }
}