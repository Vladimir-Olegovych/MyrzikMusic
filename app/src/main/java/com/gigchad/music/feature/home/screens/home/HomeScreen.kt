package com.gigchad.music.feature.home.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.gigchad.music.feature.home.contract.HomeViewModel
import com.gigchad.music.feature.shared.home.components.MusicCard
import com.gigchad.music.feature.shared.home.components.SearchBar
import com.gigchad.music.feature.shared.theme.ApplicationBrush
import com.gigchad.music.feature.shared.theme.ApplicationColors

@Composable
fun HomeScreen(
    vm: HomeViewModel
) {
    var query by remember { mutableStateOf(vm.query.value) }
    val favoriteItems by vm.favoriteEntities.collectAsState()
    val musicItems = vm.musicPagingFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        vm.updateFavoriteItems()
    }

    Column(
        modifier = Modifier
            .background(ApplicationColors.Black)
            .fillMaxSize()
    ) {
        SearchBar(
            query = query,
            onSearch = {
                vm.setQuery(it)

            },
            onQueryChange = { query = it },
            placeholder = "Search",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(musicItems.itemCount) { index ->
                    val musicData = musicItems[index]
                    if (musicData != null) {
                        MusicCard(
                            inFavorite = favoriteItems.containsKey(musicData.serverId),
                            musicData = musicData,
                            onFavorite = { value ->
                                vm.updateFavorite(musicData, value)
                            }
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(brush = ApplicationBrush.blackBrushToTransparent)
            )
        }
    }
}