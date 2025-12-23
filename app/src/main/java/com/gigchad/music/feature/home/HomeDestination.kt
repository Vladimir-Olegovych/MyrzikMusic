package com.gigchad.music.feature.home

import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gigchad.music.feature.home.contract.HomeViewModel
import com.gigchad.music.feature.home.screens.HomeDestinations
import com.gigchad.music.feature.home.screens.favorite.FavoriteScreen
import com.gigchad.music.feature.home.screens.home.HomeScreen
import com.gigchad.music.feature.home.screens.settings.SettingsScreen
import com.gigchad.music.feature.shared.theme.ApplicationColors
import com.gigchad.music.feature.shared.theme.ApplicationTypography

@Composable
fun HomeDestination() {
    val navController = rememberNavController()
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val homeViewModel: HomeViewModel = hiltViewModel()
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = ApplicationColors.YellowMain,

                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                HomeDestinations.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors().copy(
                            selectedIconColor = ApplicationColors.Black,
                            unselectedIconColor = ApplicationColors.Black,

                            selectedTextColor = ApplicationColors.White,
                            unselectedTextColor = ApplicationColors.Black
                        ),
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            navController.navigate(route = destination)
                        },
                        icon = {
                            Icon(
                                imageVector = destination.imageVector,
                                contentDescription = destination.name
                            )
                        },
                        label = {
                            Text(
                                text = destination.name,
                                style = ApplicationTypography.bodyMedium
                            )
                        }
                    )
                }
            }
        }
    ) { contentPadding ->
        NavHost(
            modifier = Modifier.padding(bottom = contentPadding.calculateBottomPadding()),
            navController = navController,
            startDestination = HomeDestinations.HomeDestination,
            enterTransition = { EnterTransition.None }
        ) {
            composable<HomeDestinations.HomeDestination> {
                HomeScreen(homeViewModel)
            }
            composable<HomeDestinations.FavoriteDestination> {
                FavoriteScreen()
            }
            composable<HomeDestinations.SettingsDestination> {
                SettingsScreen()
            }
        }
    }
}

