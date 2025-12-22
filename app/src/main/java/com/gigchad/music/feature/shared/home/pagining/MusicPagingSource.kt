package com.gigchad.music.feature.shared.home.pagining

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gigchad.domain.feature.home.interactor.HomeInteractor
import com.gigchad.domain.feature.home.models.MusicData

class MusicPagingSource(
    private val homeInteractor: HomeInteractor,
    private val query: String
) : PagingSource<Int, MusicData>() {

    override fun getRefreshKey(state: PagingState<Int, MusicData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MusicData> {
        return try {
            val page = params.key ?: 1
            val result = homeInteractor.getPage(query, page)

            if (result.isFailure) {
                throw result.exceptionOrNull() ?: Exception("Failed to load page")
            }

            val musicList = result.getOrThrow()

            LoadResult.Page(
                data = musicList,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (musicList.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}