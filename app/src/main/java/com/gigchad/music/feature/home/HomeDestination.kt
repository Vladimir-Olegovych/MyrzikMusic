package com.gigchad.music.feature.home

import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gigchad.music.feature.home.contract.HomeViewModel
import com.gigchad.music.feature.home.screens.HomeDestinations
import com.gigchad.music.feature.home.screens.favorite.FavoriteScreen
import com.gigchad.music.feature.home.screens.home.HomeScreen
import com.gigchad.music.feature.home.screens.settings.SettingsScreen
import com.gigchad.music.feature.shared.theme.ApplicationBrush
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
                            unselectedIconColor = ApplicationColors.White,

                            selectedTextColor = ApplicationColors.Black,
                            unselectedTextColor = ApplicationColors.White,
                            selectedIndicatorColor = ApplicationColors.Transparent
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
            startDestination = HomeDestinations.HomeDestination
        ) {
            composable<HomeDestinations.HomeDestination> {
                ScreenDestinationContent {
                    HomeScreen(homeViewModel)
                }
            }
            composable<HomeDestinations.FavoriteDestination> {
                ScreenDestinationContent {
                    FavoriteScreen()
                }
            }
            composable<HomeDestinations.SettingsDestination> {
                ScreenDestinationContent {
                    SettingsScreen()
                }
            }
        }
    }
}

@Composable
private fun ScreenDestinationContent(action: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        action.invoke()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(brush = ApplicationBrush.transparentBrushToYellowMain),
        )
    }
}
