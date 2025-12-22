package com.gigchad.music.core.mediaplayer

import android.media.MediaPlayer

object MediaPlayerUtils {
    private var mediaPlayer: MediaPlayer? = null

    fun play(dataUrl: String) {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null

        mediaPlayer = MediaPlayer().apply {
            setDataSource(dataUrl)
            prepareAsync()
            setOnPreparedListener {
                it.start()
            }
        }
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}