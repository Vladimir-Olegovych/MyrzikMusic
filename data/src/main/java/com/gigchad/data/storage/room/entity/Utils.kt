package com.gigchad.data.storage.room.entity

import com.gigchad.domain.feature.home.models.MusicData

fun MusicData.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        musicUrl = this.dataUrl,
        serverId = this.id,
        title = this.title,
        artist = this.artist,
        duration = this.duration,
        dataUrl = this.dataUrl,
        tags = this.tags
    )
}

fun FavoriteEntity.toMusicData(): MusicData {
    return MusicData(
        id = this.musicUrl,
        title = this.title,
        artist = this.artist,
        duration = this.duration,
        dataUrl = this.dataUrl,
        tags = this.tags
    )
}