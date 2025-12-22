package com.gigchad.music.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.gigchad.music.feature.home.vm.HomeViewModel
import com.gigchad.music.feature.shared.theme.Typography

@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        vm.getMusicList()
    }

    val musicList = vm.musicList.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color.Blue)
        ) {  }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            items(musicList.value) { musicData ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = musicData.name,
                    style = Typography.bodyLarge
                )
            }
        }
    }
}