package com.gigchad.music.feature.home.contract

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigchad.domain.feature.home.interactor.FavoriteInteractor
import com.gigchad.domain.feature.home.models.MusicData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val ioScope: CoroutineScope,
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {

    private val _favoriteEntities = MutableStateFlow<List<MusicData>>(emptyList())
    val favoriteEntities: StateFlow<List<MusicData>> = _favoriteEntities.asStateFlow()

    init {
        ioScope.launch {
            val list = favoriteInteractor.getAllFavorites()
            viewModelScope.launch {
                _favoriteEntities.value = list
            }
        }
    }

    fun removeFavorite(musicData: MusicData) {
        val currentList = _favoriteEntities.value
        val newList = currentList.toMutableList().apply {
            remove(musicData)
        }

        _favoriteEntities.value = newList

        ioScope.launch {
            favoriteInteractor.updateFavorite(musicData, false)
        }
    }
}