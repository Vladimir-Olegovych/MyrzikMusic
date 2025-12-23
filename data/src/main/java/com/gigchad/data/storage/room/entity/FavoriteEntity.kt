package com.gigchad.data.storage.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "favorite_store")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo("p_key") val pKey: String = UUID.randomUUID().toString(),
    @ColumnInfo("music_url") val musicUrl: String,
    @ColumnInfo("server_id") val serverId: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("artist") val artist: String,
    @ColumnInfo("duration") val duration: String,
    @ColumnInfo("data_url") val dataUrl: String,
    @ColumnInfo("tags") val tags: List<String> = emptyList()
)
