package com.gigchad.music.feature.shared.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gigchad.music.core.mediaplayer.MediaPlayerUtils
import com.gigchad.music.feature.shared.home.pagining.MusicPagingSource
import com.gigchad.music.feature.shared.theme.ApplicationColors
import com.gigchad.music.feature.shared.theme.ApplicationTypography
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MusicCard(item: MusicPagingSource.Item, onFavorite: (Boolean) -> Unit) {

    val musicData = item.musicData
    val inFavorite = MutableStateFlow(item.inFavorite)
    val inFavoriteState = inFavorite.collectAsState()

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(ApplicationColors.White)
            .padding(10.dp)
            .fillMaxWidth()
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    MediaPlayerUtils.play(musicData.dataUrl)
                })
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "${musicData.title} | ${musicData.artist}",
                style = ApplicationTypography.bodyLarge,
                color = ApplicationColors.Black
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                FlowRow(
                    horizontalGap = 8.dp,
                    verticalGap = 4.dp
                ) {
                    repeat(musicData.tags.size) { index ->
                        val tag = musicData.tags[index]
                        Text(
                            text = "*$tag",
                            style = ApplicationTypography.bodyMedium,
                            color = ApplicationColors.Black
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = musicData.duration,
                style = ApplicationTypography.bodyMedium,
                color = ApplicationColors.Black
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier.clickable(
                        onClick = {
                            inFavorite.value = !inFavorite.value
                            item.inFavorite = inFavorite.value
                            onFavorite.invoke(inFavorite.value)
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                    imageVector = if(inFavoriteState.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null
                )
            }
        }
    }
}