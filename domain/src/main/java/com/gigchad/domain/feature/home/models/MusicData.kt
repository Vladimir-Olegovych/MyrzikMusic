package com.gigchad.domain.feature.home.models

data class MusicData(
    val serverId: String = "0",
    val title: String = "?",
    val artist: String = "?",
    val duration: String = "0",
    val dataUrl: String = "",
    val tags: List<String> = emptyList()
)