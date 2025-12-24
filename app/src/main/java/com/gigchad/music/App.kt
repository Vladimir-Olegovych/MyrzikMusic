package com.gigchad.music

import android.app.Application
import com.gigchad.music.service.MusicPlayerService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        MusicPlayerService.bindNotification(this)
    }
}