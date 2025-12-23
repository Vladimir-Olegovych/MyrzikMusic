package com.gigchad.data.storage.datastore.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gigchad.data.storage.datastore.DataStoreClient
import kotlinx.coroutines.flow.first

class DataStoreClientImpl(private val dataStore: DataStore<Preferences>) : DataStoreClient {

    override suspend fun setString(key: String, value: String) =
        setValue(stringPreferencesKey(key), value)

    override suspend fun getString(key: String, default: String): String =
        getValue(stringPreferencesKey(key), default)

    override suspend fun setBoolean(key: String, value: Boolean) =
        setValue(booleanPreferencesKey(key), value)

    override suspend fun getBoolean(key: String, default: Boolean): Boolean =
        getValue(booleanPreferencesKey(key), default)

    private suspend fun <T> getValue(key: Preferences.Key<T>, default: T): T =
        dataStore.data.first().get(key) ?: default

    private suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

}