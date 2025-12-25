package com.gigchad.music

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gigchad.music.core.navigation.HomeDestination
import com.gigchad.music.core.permission.PermissionUtils
import com.gigchad.music.feature.home.HomeDestination
import com.gigchad.music.feature.shared.theme.MyrzikMusicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionUtils.registerPermissionRequests(this)
        enableEdgeToEdge()
        setContent {
            MyrzikMusicTheme {
                BeginContent()
            }
        }
    }
}

@Composable
fun BeginContent(){
    val context = LocalContext.current
    var hasPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(PermissionUtils.hasPermission(context, POST_NOTIFICATIONS))
        } else {
            mutableStateOf(true)
        }
    }
    if (!hasPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        LaunchedEffect(Unit) {
            PermissionUtils.requestPermission(POST_NOTIFICATIONS, onSuccess = {
                hasPermission = true
            })
        }
        return
    }
    BindNavigation()
}
@Composable
fun BindNavigation() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        content = { contentPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = HomeDestination,
                    enterTransition = { EnterTransition.None }
                ) {
                    composable<HomeDestination> {
                        HomeDestination()
                    }
                }
            }
        }
    )
}