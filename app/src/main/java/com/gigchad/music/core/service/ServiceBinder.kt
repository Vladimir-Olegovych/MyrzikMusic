package com.gigchad.music.core.service

import android.app.Service
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ServiceBinder<T: Service, B: AdaptiveServiceBinder<*>>() {

    private val service = MutableStateFlow<T?>(null)
    private val isBound = MutableStateFlow(false)

    fun getService() = service.asStateFlow()
    fun isBound() = isBound.asStateFlow()

    val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            binder: IBinder?
        ) {
            service.update { (binder as B).getService() as T }
            isBound.update { true }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service.update { null }
            isBound.update { false }
        }

    }

}