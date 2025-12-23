package com.gigchad.data.storage.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.gigchad.data.storage.datastore.DataStoreClient
import com.gigchad.data.storage.datastore.impl.DataStoreClientImpl
import com.gigchad.data.storage.room.MusicDatabase
import com.gigchad.data.storage.room.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            File("${context.filesDir}/datastore/main.preferences_pb")
        }

    @Provides
    @Singleton
    fun provideDataStoreClient(dataStore: DataStore<Preferences>): DataStoreClient =
        DataStoreClientImpl(dataStore)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MusicDatabase =
        Room.databaseBuilder(context, MusicDatabase::class.java, name = "database.db").fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideFavoriteDao(database: MusicDatabase): FavoriteDao = database.getFavoriteDao()

}