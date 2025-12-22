package com.gigchad.music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gigchad.music.core.mediaplayer.MediaPlayerUtils
import com.gigchad.music.core.navigation.HomeDestination
import com.gigchad.music.feature.home.HomeScreen
import com.gigchad.music.feature.shared.theme.MyrzikMusicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyrzikMusicTheme {
                BindNavigation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MediaPlayerUtils.stop()
    }
}

@Composable
fun BindNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeDestination,
        enterTransition = { EnterTransition.None }
    ) {
        composable<HomeDestination> {
            HomeScreen()
        }
    }
}