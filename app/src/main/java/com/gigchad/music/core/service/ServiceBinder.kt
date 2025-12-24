package com.gigchad.music.core.service

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlin.jvm.java

class ServiceBinder<T: Service, B: AdaptiveServiceBinder<*>>() {

    private var service: T? = null
    private var isBound = false

    fun getService(): T = service!!
    fun isBound(): Boolean = isBound

    val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            binder: IBinder?
        ) {
            service = (binder as B).getService() as T
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

    }

}