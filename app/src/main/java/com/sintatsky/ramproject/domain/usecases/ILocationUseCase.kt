package com.sintatsky.ramproject.domain.usecases

import com.sintatsky.ramproject.domain.entities.LocationFilter
import com.sintatsky.ramproject.domain.entities.LocationInfo
import com.sintatsky.ramproject.domain.entities.Locations

interface ILocationUseCase {

    suspend fun getAllLocations(page: Int, filter: LocationFilter): Locations

    suspend fun getLocationById(id: Int): LocationInfo

    suspend fun getLocationsById(id: String): List<LocationInfo>
}