package com.sintatsky.ramproject.presentation.features.episodes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sintatsky.ramproject.domain.entities.EpisodeInfo
import com.sintatsky.ramproject.domain.entities.EpisodesFilter
import com.sintatsky.ramproject.domain.usecases.IEpisodeUseCase
import com.sintatsky.ramproject.presentation.features.base.BaseViewModel
import com.sintatsky.ramproject.presentation.features.episodes.ui.EpisodesFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val episodesUseCase: IEpisodeUseCase
) : BaseViewModel() {

    private val _episodesLiveData: MutableLiveData<List<EpisodeInfo>> = MutableLiveData()
    val episodesLiveData: LiveData<List<EpisodeInfo>>
        get() = _episodesLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    private val _isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean>
        get() = _isErrorLiveData

    fun loadEpisodes(page: Int, filter: EpisodesFilter) {
        isLoading(true)
        viewModelScope.launch {
            val result = episodesUseCase.getAllEpisodes(page, filter)
            EpisodesFragment.pages = result.info.pages
            updateLiveData(result.results, 0)
        }
    }

    fun reloadEpisodes(filter: EpisodesFilter) {
        isLoading(true)
        viewModelScope.launch {
            val result = episodesUseCase.getAllEpisodes(1, filter)
            EpisodesFragment.pages = result.info.pages
            updateLiveData(result.results, 1)
        }
    }

    private fun updateLiveData(episodes: List<EpisodeInfo>, flag: Int) {
        if (!episodes.isNullOrEmpty()) {
            onSuccess(episodes, flag)
        } else {
            onError()
        }
    }

    private fun onError() {
        viewModelScope.launch {
            delay(300)
            isLoading(false)
        }.invokeOnCompletion {
            _isErrorLiveData.value = true
        }
    }

    private fun onSuccess(episodes: List<EpisodeInfo>, flag: Int) {
        when (flag) {
            0 -> if (!_episodesLiveData.value.isNullOrEmpty()) {
                _episodesLiveData.value = _episodesLiveData.value?.plus(episodes)
            } else {
                _episodesLiveData.value = episodes
            }
            1 -> _episodesLiveData.value = episodes
        }
        isLoading(false)
    }

    private fun isLoading(isLoading: Boolean) {
        this._isLoadingLiveData.value = isLoading
    }
}