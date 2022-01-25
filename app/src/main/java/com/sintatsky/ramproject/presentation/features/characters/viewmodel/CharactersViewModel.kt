package com.sintatsky.ramproject.presentation.features.characters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.sintatsky.ramproject.domain.entities.CharactersFilter
import com.sintatsky.ramproject.domain.usecases.ICharacterUseCase
import com.sintatsky.ramproject.presentation.features.base.BaseViewModel
import com.sintatsky.ramproject.presentation.features.characters.ui.CharactersFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val characterUseCase: ICharacterUseCase
) : BaseViewModel() {

    private val _charactersLiveData: MutableLiveData<List<CharacterInfo>> = MutableLiveData()
    val charactersLiveData: LiveData<List<CharacterInfo>>
        get() = _charactersLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    private val _isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean>
        get() = _isErrorLiveData

    fun loadCharacters(page: Int, filter: CharactersFilter) {
        isLoading(true)
        viewModelScope.launch {
            val result = characterUseCase.getAllCharacters(page, filter)
            CharactersFragment.pages = result.info.pages
            updateLiveData(result.results, 0)
        }
    }

    fun reloadCharacters(filter: CharactersFilter) {
        isLoading(true)
        viewModelScope.launch {
            val result = characterUseCase.getAllCharacters(1, filter)
            CharactersFragment.pages = result.info.pages
            updateLiveData(result.results, 1)
        }
    }

    private fun updateLiveData(characters: List<CharacterInfo>, flag: Int) {
        if (!characters.isNullOrEmpty()) {
            onSuccess(characters, flag)
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

    private fun onSuccess(characters: List<CharacterInfo>, flag: Int) {
        when (flag) {
            0 -> if (!_charactersLiveData.value.isNullOrEmpty()) {
                _charactersLiveData.value = _charactersLiveData.value?.plus(characters)
            } else {
                _charactersLiveData.value = characters
            }
            1 -> _charactersLiveData.value = characters
        }
        isLoading(false)
    }

    private fun isLoading(isLoading: Boolean) {
        this._isLoadingLiveData.value = isLoading
    }
}