package com.gigchad.data.storage.room.converter


import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class ListTypeConverter {

    @TypeConverter
    fun fromStringToStringList(value: String): List<String> =
        Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromStringListToString(value: List<String>): String = Json.encodeToString(value)

}