package com.gigchad.music.feature.home.contract

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gigchad.domain.feature.home.interactor.HomeInteractor
import com.gigchad.domain.feature.home.models.MusicData
import com.gigchad.music.feature.shared.home.pagining.MusicPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ioScope: CoroutineScope,
    private val homeInteractor: HomeInteractor
) : ViewModel() {

    private val _favoriteEntities = MutableStateFlow<Map<String, MusicData>>(emptyMap())
    val favoriteEntities: StateFlow<Map<String, MusicData>> = _favoriteEntities.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    init {
        _query.value = ""
    }

    @OptIn(FlowPreview::class)
    private val searchFlow = _query
        .debounce(300)
        .distinctUntilChanged()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            replay = 1
        )

    val musicPagingFlow = searchFlow.flatMapLatest { currentQuery ->
        Pager(
            config = PagingConfig(
                pageSize = 50,
                prefetchDistance = 5,
                enablePlaceholders = false
            )
        ) {
            MusicPagingSource(homeInteractor, currentQuery)
        }.flow
    }.cachedIn(ioScope)


    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun updateFavoriteItems() {
        viewModelScope.launch {
            val favorites = ioScope.async {
                homeInteractor.getAllFavorites()
            }.await()

            val map = favorites.associateBy { it.serverId }
            _favoriteEntities.value = map
        }
    }

    fun updateFavorite(musicData: MusicData, value: Boolean) {
        updateFavoriteLocally(musicData, value)

        ioScope.launch {
            homeInteractor.updateFavorite(musicData, value)
        }
    }

    private fun updateFavoriteLocally(musicData: MusicData, value: Boolean) {
        val currentFavorites = _favoriteEntities.value.toMutableMap()

        if (value) {
            currentFavorites[musicData.serverId] = musicData
        } else {
            currentFavorites.remove(musicData.serverId)
        }

        _favoriteEntities.value = currentFavorites.toMap()
    }
}