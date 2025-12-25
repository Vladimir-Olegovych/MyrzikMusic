package com.gigchad.music.core.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat


object PermissionUtils {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private var onSuccess: (() -> Unit)? = null
    private var onFailure: (() -> Unit)? = null

    fun registerPermissionRequests(activity: ComponentActivity){
        requestPermissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                onSuccess?.invoke()
            } else {
                onFailure?.invoke()
            }
            onSuccess = null
            onFailure = null
        }
    }

    fun requestPermission(
        permission: String,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {}
    ) {
        if (this.onSuccess != null || this.onFailure != null) {
            onFailure.invoke()
            return
        }
        this.onSuccess = onSuccess
        this.onFailure = onFailure
        requestPermissionLauncher.launch(permission)
    }

    fun hasPermission(
        context: Context,
        permission: String
    ): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}