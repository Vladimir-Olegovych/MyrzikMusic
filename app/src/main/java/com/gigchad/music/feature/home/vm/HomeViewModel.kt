package com.gigchad.music.feature.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigchad.domain.feature.home.interactor.HomeInteractor
import com.gigchad.domain.feature.home.models.MusicData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val homeInteractor: HomeInteractor
): ViewModel() {
    private val _musicList = MutableStateFlow<List<MusicData>>(emptyList())
    val musicList: StateFlow<List<MusicData>> = _musicList

    fun getMusicList(){
        viewModelScope.launch(Dispatchers.IO) {
            homeInteractor.getMusic().fold(
                onSuccess = {
                    _musicList.value = it
                },
                onFailure = { it.printStackTrace() }
            )
        }
    }
}