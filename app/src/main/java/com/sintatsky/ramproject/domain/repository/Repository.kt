package com.sintatsky.ramproject.domain.repository

import com.sintatsky.ramproject.domain.entities.*

interface Repository {

    suspend fun getAllCharacters(page: Int, filter: CharactersFilter): Characters

    suspend fun getCharacterById(id: Int): CharacterInfo

    suspend fun getCharactersById(id: String): List<CharacterInfo>

    suspend fun getAllLocations(page: Int, filter: LocationFilter): Locations

    suspend fun getLocationById(id: Int): LocationInfo

    suspend fun getLocationsById(id: String): List<LocationInfo>

    suspend fun getAllEpisodes(page: Int, filter: EpisodesFilter): Episodes

    suspend fun getEpisodeById(id: Int): EpisodeInfo

    suspend fun getEpisodesById(id: String): List<EpisodeInfo>
}