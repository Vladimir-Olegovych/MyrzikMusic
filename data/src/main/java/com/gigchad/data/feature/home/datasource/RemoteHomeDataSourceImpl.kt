package com.gigchad.data.feature.home.datasource

import com.gigchad.data.feature.home.datasource.repository.RemoteHomeDataSourceRepository
import com.gigchad.domain.feature.home.models.MusicData
import org.jsoup.Jsoup

class RemoteHomeDataSourceImpl: RemoteHomeDataSourceRepository {

    companion object {
        const val TIME_OUT = 10000
        const val WEBSITE_URL = "https://muzofond.fm"
        const val WEBSITE_SEARCH = "https://muzofond.fm/search"
    }

    override suspend fun getPage(query: String, page: Int): Result<List<MusicData>> {
        return runCatching {
            val resultList = ArrayList<MusicData>()

            val urlType = when {
                query.isNotEmpty() -> "$WEBSITE_SEARCH/$query"
                else -> WEBSITE_URL
            }

            val url = when {
                page > 1 -> "$urlType/$page"
                else -> urlType
            }
            println(url)

            val doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(TIME_OUT)
                .get()
            val mainContainer = doc.selectFirst("div.module-layout.boxShadow")
                ?: throw Throwable("container not found")

            val musicContainers = mainContainer.select("li.item")

            musicContainers.forEachIndexed { index, container ->

                val trackId = container.attr("data-id")

                val titleElement = container.selectFirst("span.track")
                val title = titleElement?.text() ?: ""

                val artistElement = container.selectFirst("span.artist")
                val artist = artistElement?.text() ?: ""

                val durationElement = container.selectFirst("div.duration.enemy")
                val duration = durationElement?.text()?.trim() ?: ""

                val playButton = container.selectFirst("li.play")
                val dataUrl = playButton?.attr("data-url")?: ""

                val tagElements = container.select("div.description span a")
                val tags = tagElements.map { it.text() }

                resultList.add(
                    MusicData(
                        id = trackId,
                        title = title,
                        artist = artist,
                        duration = duration,
                        dataUrl = dataUrl,
                        tags = tags
                    )
                )
            }
            return@runCatching resultList
        }
    }
}