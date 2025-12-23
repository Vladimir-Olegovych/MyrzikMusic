package com.gigchad.data.storage.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gigchad.data.storage.room.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    /**
     * Вставить или обновить запись
     * OnConflictStrategy.REPLACE заменит существующую запись при конфликте
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(favorite: FavoriteEntity)

    /**
     * Вставить или обновить несколько записей
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAll(favorites: List<FavoriteEntity>)

    /**
     * Удалить запись по ключу (musicUrl)
     */
    @Query("DELETE FROM favorite_store WHERE music_url = :musicUrl")
    suspend fun deleteByUrl(musicUrl: String): Int

    /**
     * Удалить несколько записей по ключам
     */
    @Query("DELETE FROM favorite_store WHERE music_url IN (:musicUrls)")
    suspend fun deleteByUrls(musicUrls: List<String>): Int

    /**
     * Удалить запись по объекту
     */
    @Delete
    suspend fun delete(favorite: FavoriteEntity): Int

    /**
     * Удалить несколько записей
     */
    @Delete
    suspend fun deleteAll(favorites: List<FavoriteEntity>): Int

    /**
     * Получить все избранные записи
     */
    @Query("SELECT * FROM favorite_store")
    suspend fun getAll(): List<FavoriteEntity>

    /**
     * Получить все избранные записи с возможностью лимита
     */
    @Query("SELECT * FROM favorite_store LIMIT :limit")
    suspend fun getAllWithLimit(limit: Int): List<FavoriteEntity>

    /**
     * Получить конкретную запись по ключу
     */
    @Query("SELECT * FROM favorite_store WHERE music_url = :musicUrl LIMIT 1")
    suspend fun getByUrl(musicUrl: String): FavoriteEntity?

    /**
     * Проверить, существует ли запись
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_store WHERE music_url = :musicUrl LIMIT 1)")
    suspend fun exists(musicUrl: String): Boolean

    /**
     * Получить количество записей
     */
    @Query("SELECT COUNT(*) FROM favorite_store")
    suspend fun getCount(): Int

    /**
     * Удалить все записи
     */
    @Query("DELETE FROM favorite_store")
    suspend fun deleteAll()
}