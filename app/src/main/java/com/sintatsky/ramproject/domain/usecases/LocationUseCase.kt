package com.sintatsky.ramproject.domain.usecases

import com.sintatsky.ramproject.domain.entities.LocationFilter
import com.sintatsky.ramproject.domain.entities.LocationInfo
import com.sintatsky.ramproject.domain.entities.Locations
import com.sintatsky.ramproject.domain.repository.Repository
import javax.inject.Inject

class LocationUseCase @Inject constructor(
    private val repository: Repository
) : ILocationUseCase {
    override suspend fun getAllLocations(page: Int, filter: LocationFilter): Locations {
        return repository.getAllLocations(page, filter)
    }

    override suspend fun getLocationById(id: Int): LocationInfo {
        return repository.getLocationById(id)
    }

    override suspend fun getLocationsById(id: String): List<LocationInfo> {
        return repository.getLocationsById(id)
    }
}