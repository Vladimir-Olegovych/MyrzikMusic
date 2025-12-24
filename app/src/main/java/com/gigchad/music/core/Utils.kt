package com.gigchad.music.core

import android.media.MediaPlayer
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import com.gigchad.domain.feature.home.models.MusicData
import kotlinx.serialization.json.Json

inline fun <reified T : Any> serializableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {

    override fun put(bundle: SavedState, key: String, value: T) {
        bundle.write { putString(key, json.encodeToString(value)) }
    }

    override fun get(bundle: SavedState, key: String): T? {
        return json.decodeFromString<T?>(bundle.read { getString(key) })
    }

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)
}


fun MediaPlayer.playMusicData(musicData: MusicData, onPreparedListener: () -> Unit){
    setDataSource(musicData.dataUrl)
    prepareAsync()
    setOnPreparedListener {
        it.start()
        onPreparedListener.invoke()
    }
}