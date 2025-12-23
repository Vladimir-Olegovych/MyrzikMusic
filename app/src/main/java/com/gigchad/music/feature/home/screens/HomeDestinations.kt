package com.gigchad.music.feature.home.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.gigchad.music.core.navigation.Destination
import kotlinx.serialization.Serializable

sealed interface HomeDestinations: Destination {

    val imageVector: ImageVector
    val name: String
    @Serializable
    data object HomeDestination: HomeDestinations {
        override val imageVector: ImageVector = Icons.Default.Home
        override val name: String = "Home"
    }

    @Serializable
    data object FavoriteDestination: HomeDestinations {
        override val imageVector: ImageVector = Icons.Default.Favorite
        override val name: String = "Favorite"
    }

    @Serializable
    data object SettingsDestination: HomeDestinations {
        override val imageVector: ImageVector = Icons.Default.Settings
        override val name: String = "Settings"
    }

    companion object {
        val entries = arrayOf(HomeDestination, FavoriteDestination, SettingsDestination)
    }

}