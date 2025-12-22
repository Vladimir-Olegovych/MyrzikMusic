package com.gigchad.music.feature.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.gigchad.domain.feature.home.interactor.HomeInteractor
import com.gigchad.music.feature.shared.home.pagining.MusicPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val ioScope: CoroutineScope,
    private val homeInteractor: HomeInteractor
): ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    init { setQuery("") }

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

}