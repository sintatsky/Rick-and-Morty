package com.sintatsky.ramproject.presentation.features.locations.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sintatsky.ramproject.domain.entities.LocationFilter
import com.sintatsky.ramproject.domain.entities.LocationInfo
import com.sintatsky.ramproject.domain.usecases.LocationUseCase
import com.sintatsky.ramproject.presentation.features.base.BaseViewModel
import com.sintatsky.ramproject.presentation.features.locations.ui.LocationsFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase
) : BaseViewModel() {

    private val _locationsLiveData: MutableLiveData<List<LocationInfo>> = MutableLiveData()
    val locationsLiveData: LiveData<List<LocationInfo>>
        get() = _locationsLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    private val _isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean>
        get() = _isErrorLiveData

    fun loadLocations(page: Int, filter: LocationFilter) {
        isLoading(true)
        viewModelScope.launch {
            val result = locationUseCase.getAllLocations(page, filter)
            LocationsFragment.pages = result.info.pages
            updateLiveData(result.results, 0)
        }
    }

    fun reloadLocations(filter: LocationFilter) {
        isLoading(true)
        viewModelScope.launch {
            val result = locationUseCase.getAllLocations(1, filter)
            LocationsFragment.pages = result.info.pages
            updateLiveData(result.results, 1)
        }
    }

    private fun updateLiveData(locations: List<LocationInfo>, flag: Int) {
        if (!locations.isNullOrEmpty()) {
            onSuccess(locations, flag)
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

    private fun onSuccess(locations: List<LocationInfo>, flag: Int) {
        when (flag) {
            0 -> if (!_locationsLiveData.value.isNullOrEmpty()) {
                _locationsLiveData.value = _locationsLiveData.value?.plus(locations)
            } else {
                _locationsLiveData.value = locations
            }
            1 -> _locationsLiveData.value = locations
        }
        isLoading(false)
    }

    private fun isLoading(isLoading: Boolean) {
        this._isLoadingLiveData.value = isLoading
    }
}