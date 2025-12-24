package com.gigchad.music.core.service

import android.app.Service
import android.os.Binder

abstract class AdaptiveServiceBinder<T: Service>: Binder() {
    abstract fun getService(): T
}