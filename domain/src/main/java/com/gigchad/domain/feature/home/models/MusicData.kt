package com.gigchad.domain.feature.home.models

data class MusicData(
    val id: String,
    val title: String,
    val artist: String,
    val duration: String,
    val dataUrl: String,
    val tags: List<String>
)