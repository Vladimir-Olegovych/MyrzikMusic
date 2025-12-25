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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gigchad.domain.feature.home.models.MusicData
import com.gigchad.music.feature.shared.theme.ApplicationColors
import com.gigchad.music.feature.shared.theme.ApplicationTypography

@Composable
fun MusicCard(
    inFavorite: Boolean,
    musicData: MusicData,
    onFavorite: (Boolean) -> Unit
) {
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
                    //MediaPlayerUtils.play(musicData.dataUrl)
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
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier.clickable(
                        onClick = {
                            onFavorite.invoke(!inFavorite)

                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                    imageVector = if(inFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = ApplicationColors.Black,
                    contentDescription = null
                )
            }
        }
    }
}