package com.sintatsky.ramproject.presentation.features.locations.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.sintatsky.ramproject.domain.entities.LocationInfo
import com.sintatsky.ramproject.domain.usecases.ICharacterUseCase
import com.sintatsky.ramproject.domain.usecases.LocationUseCase
import com.sintatsky.ramproject.presentation.features.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LocationDetailViewModel @Inject constructor(
    private val characterUseCase: ICharacterUseCase,
    private val locationUseCase: LocationUseCase
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

    fun loadCharacters(charactersList: List<String>) {
        isLoading(true)
        viewModelScope.launch {
            var characters = ""
            for (character in charactersList) {
                val id = character.split("/").last()
                characters += "$id,"
            }
            val result = characterUseCase.getCharactersById(characters)
            updateLiveData(result)
        }
    }

    fun loadLocation(locationId: Int): LocationInfo? {
        var location: LocationInfo? = null
        runBlocking {
            val getLocation = async {
                locationUseCase.getLocationById(locationId)
            }
            location = getLocation.await()
        }
        return location
    }

    private fun updateLiveData(characters: List<CharacterInfo>) {
        if (!characters.isNullOrEmpty()) {
            onSuccess(characters)
        } else {
            onError()
        }
    }

    private fun onSuccess(characters: List<CharacterInfo>) {
        _charactersLiveData.value = characters
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