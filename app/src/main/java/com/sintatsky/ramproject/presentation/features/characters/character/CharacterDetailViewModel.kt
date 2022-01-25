package com.sintatsky.ramproject.presentation.features.characters.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.sintatsky.ramproject.domain.entities.EpisodeInfo
import com.sintatsky.ramproject.domain.usecases.ICharacterUseCase
import com.sintatsky.ramproject.domain.usecases.IEpisodeUseCase
import com.sintatsky.ramproject.presentation.features.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(
    private val characterUseCase: ICharacterUseCase,
    private val episodeUseCase: IEpisodeUseCase
) : BaseViewModel() {

    private val _episodeLiveData: MutableLiveData<List<EpisodeInfo>> = MutableLiveData()
    val episodeLiveData: LiveData<List<EpisodeInfo>>
        get() = _episodeLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    private val _isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean>
        get() = _isErrorLiveData

    fun loadEpisodes(episodeList: List<String>) {
        isLoading(true)
        viewModelScope.launch {
            var episodes = ""
            for (episode in episodeList) {
                val id = episode.split("/").last()
                episodes += "$id,"
            }
            val result = episodeUseCase.getEpisodesById(episodes)
            updateLiveData(result)
        }
    }

    fun loadCharacter(characterId: Int): CharacterInfo? {
        var character: CharacterInfo? = null
        runBlocking {
            val getCharacter = async {
                characterUseCase.getCharacterById(characterId)
            }
            character = getCharacter.await()
        }
        return character
    }

    private fun updateLiveData(episodes: List<EpisodeInfo>) {
        if (!episodes.isNullOrEmpty()) {
            onSuccess(episodes)
        } else {
            onError()
        }
    }

    private fun onSuccess(episodes: List<EpisodeInfo>) {
        _episodeLiveData.value = episodes
        isLoading(false)
    }

    private fun onError() {
        viewModelScope.launch {
            delay(300)
            isLoading(false)
        }.invokeOnCompletion {
            _isErrorLiveData.value = true
        }
    }

    private fun isLoading(isLoading: Boolean) {
        this._isLoadingLiveData.value = isLoading
    }
}