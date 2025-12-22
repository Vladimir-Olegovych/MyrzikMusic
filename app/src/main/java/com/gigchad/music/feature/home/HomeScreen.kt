package com.gigchad.music.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.gigchad.music.core.mediaplayer.MediaPlayerUtils
import com.gigchad.music.feature.home.vm.HomeViewModel
import com.gigchad.music.feature.shared.home.components.SearchBar
import com.gigchad.music.feature.shared.theme.Typography

@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    val musicItems = vm.musicPagingFlow.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(top = 20.dp)

    ) {
        SearchBar(
            query = query,
            onSearch = { vm.setQuery(it) },
            onQueryChange = { query = it },
            placeholder = "Search",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp, start = 24.dp, end = 24.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(musicItems.itemCount) { index ->
                val item = musicItems[index]
                if (item != null) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clickable(onClick = {
                                MediaPlayerUtils.play(item.dataUrl)
                            }),
                        text = item.title,
                        style = Typography.bodyLarge
                    )
                }
            }
        }
    }
}