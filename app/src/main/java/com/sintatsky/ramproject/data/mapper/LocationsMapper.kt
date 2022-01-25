package com.sintatsky.ramproject.data.mapper

import com.sintatsky.ramproject.data.model.LocationDb
import com.sintatsky.ramproject.domain.entities.LocationInfo
import javax.inject.Inject

class LocationsMapper @Inject constructor() {

    fun mapFromInfoToDbList(type: List<LocationInfo>): List<LocationDb> {
        return type.map {
            LocationDb(
                id = it.id,
                name = it.name,
                type = it.type,
                dimension = it.dimension,
                url = it.url
            )
        }
    }

    fun mapFromDbToInfoList(type: List<LocationDb>): List<LocationInfo> {
        return type.map {
            LocationInfo(
                id = it.id,
                name = it.name,
                type = it.type,
                dimension = it.dimension,
                url = it.url,
                characters = null
            )
        }
    }

    fun mapFromDbToInfo(type: LocationDb): LocationInfo {
        return LocationInfo(
            id = type.id,
            name = type.name,
            type = type.type,
            dimension = type.dimension,
            url = type.url,
            characters = null
        )
    }
}