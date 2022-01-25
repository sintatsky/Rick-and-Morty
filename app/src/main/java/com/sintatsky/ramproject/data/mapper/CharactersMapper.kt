package com.sintatsky.ramproject.data.mapper

import com.sintatsky.ramproject.data.model.CharacterDb
import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.sintatsky.ramproject.domain.entities.CharacterLocation
import javax.inject.Inject

class CharactersMapper  @Inject constructor(){

    fun mapFromInfoToDbList(type: List<CharacterInfo>): List<CharacterDb>{
       return type.map {
            CharacterDb(
                id = it.id,
                name = it.name,
                species = it.species,
                type = it.type,
                status = it.status,
                gender = it.gender,
                image = it.image,
                origin = it.origin.name,
                originUrl = it.origin.url,
                location = it.location.name,
                locationUrl = it.location.url
            )
        }
    }

    fun mapFromDbToInfoList(type: List<CharacterDb>): List<CharacterInfo>{
        return type.map {
            CharacterInfo(
                id = it.id,
                name = it.name,
                species = it.species,
                type = it.type,
                status = it.status,
                gender = it.gender,
                image = it.image,
                origin = CharacterLocation(it.origin, it.originUrl),
                location = CharacterLocation(it.location, it.locationUrl),
                episode = null
            )
        }
    }

    fun mapFromDbToInfo(type: CharacterDb) : CharacterInfo{
        return CharacterInfo(
            id = type.id,
            name = type.name,
            species = type.species,
            type = type.type,
            status = type.status,
            gender = type.gender,
            image = type.image,
            origin = CharacterLocation(type.origin, type.originUrl),
            location = CharacterLocation(type.location, type.locationUrl),
            episode = null
        )
    }
}