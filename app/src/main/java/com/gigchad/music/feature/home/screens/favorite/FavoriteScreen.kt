package com.gigchad.music.feature.home.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.gigchad.music.feature.home.contract.FavoriteViewModel
import com.gigchad.music.feature.shared.home.components.MusicCard
import com.gigchad.music.feature.shared.theme.ApplicationBrush
import com.gigchad.music.feature.shared.theme.ApplicationColors

@Composable
fun FavoriteScreen(
    vm: FavoriteViewModel = hiltViewModel()
) {
    val musicItems = vm.favoriteEntities.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ApplicationColors.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(musicItems) { musicData ->
                MusicCard(
                    inFavorite = true,
                    musicData = musicData,
                    onFavorite = {
                        vm.removeFavorite(musicData)
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(brush = ApplicationBrush.yellowMainBrushToTransparent)
        )
    }
}