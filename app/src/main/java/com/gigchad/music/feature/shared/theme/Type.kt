package com.gigchad.music.feature.shared.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
object ApplicationTypography {
    val bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    val bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

}

object ApplicationBrush {
    val blackBrushToTransparent = Brush.linearGradient(
        colors = listOf(
            ApplicationColors.Black,
            ApplicationColors.Transparent
        ),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    val yellowMainBrushToTransparent = Brush.linearGradient(
        colors = listOf(
            ApplicationColors.YellowMain,
            ApplicationColors.Transparent
        ),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    val transparentBrushToYellowMain = Brush.linearGradient(
        colors = listOf(
            ApplicationColors.Transparent,
            ApplicationColors.YellowMain
        ),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )
}