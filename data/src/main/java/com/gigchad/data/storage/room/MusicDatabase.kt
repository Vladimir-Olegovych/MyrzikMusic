package com.gigchad.data.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gigchad.data.storage.room.converter.ListTypeConverter
import com.gigchad.data.storage.room.dao.FavoriteDao
import com.gigchad.data.storage.room.entity.FavoriteEntity

@TypeConverters(value = [ListTypeConverter::class])
@Database(
    entities = [FavoriteEntity::class],
    version = 6,
    exportSchema = false
)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao


}