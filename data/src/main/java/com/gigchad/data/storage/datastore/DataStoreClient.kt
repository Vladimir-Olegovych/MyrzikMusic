package com.gigchad.data.storage.datastore

interface DataStoreClient {
    suspend fun getString(key: String, default: String = ""): String
    suspend fun setString(key: String, value: String)
    suspend fun getBoolean(key: String, default: Boolean = false): Boolean
    suspend fun setBoolean(key: String, value: Boolean)
}