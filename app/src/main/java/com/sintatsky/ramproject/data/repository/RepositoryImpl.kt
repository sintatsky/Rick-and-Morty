package com.sintatsky.ramproject.data.repository

import com.sintatsky.ramproject.data.database.RickAndMortyDao
import com.sintatsky.ramproject.data.mapper.CharactersMapper
import com.sintatsky.ramproject.data.mapper.EpisodesMapper
import com.sintatsky.ramproject.data.mapper.LocationsMapper
import com.sintatsky.ramproject.data.network.RickAndMortyApi
import com.sintatsky.ramproject.domain.entities.*
import com.sintatsky.ramproject.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val rickAndMortyDao: RickAndMortyDao,
    private val rickAndMortyApi: RickAndMortyApi,
    private val charactersMapper: CharactersMapper,
    private val locationsMapper: LocationsMapper,
    private val episodesMapper: EpisodesMapper,
) : Repository {

    override suspend fun getAllCharacters(page: Int, filter: CharactersFilter) = try {
        val result = rickAndMortyApi.getAllCharacters(
            page,
            filter.name,
            filter.status,
            filter.gender,
            filter.species
        )
        rickAndMortyDao.saveAllCharacters(charactersMapper.mapFromInfoToDbList(result.results))
        result
    } catch (e: Exception) {
        val list = charactersMapper.mapFromDbToInfoList(
            rickAndMortyDao.getAllCharacters(
                if (filter.name.isNullOrEmpty()) filter.name else "%${filter.name}%",
                filter.status,
                filter.gender,
                filter.species
            )
        )
        Characters(list, InfoResult(1))
    }


    override suspend fun getCharacterById(id: Int): CharacterInfo = try {
        rickAndMortyApi.getCharacterById(id)
    } catch (e: Exception) {
        charactersMapper.mapFromDbToInfo(rickAndMortyDao.getCharacterById(id))
    }

    override suspend fun getCharactersById(id: String): List<CharacterInfo> = try {
        rickAndMortyApi.getCharactersById(id)
    } catch (e: Exception) {
        charactersMapper.mapFromDbToInfoList(rickAndMortyDao.getCharactersById(id))
    }

    override suspend fun getAllLocations(page: Int, filter: LocationFilter) = try {
        val result = rickAndMortyApi.getAllLocations(page, filter.name, filter.type)
        rickAndMortyDao.saveAllLocations(locationsMapper.mapFromInfoToDbList(result.results))
        result
    } catch (e: Exception) {
        val list = locationsMapper.mapFromDbToInfoList(
            rickAndMortyDao.getAllLocations(
                if (filter.name.isNullOrEmpty()) filter.name else "%${filter.name}%",
                filter.type
            )
        )
        Locations(list, InfoResult(1))
    }

    override suspend fun getLocationById(id: Int): LocationInfo = try {
        rickAndMortyApi.getLocationById(id)
    } catch (e: Exception) {
        locationsMapper.mapFromDbToInfo(rickAndMortyDao.getLocationById(id))
    }

    override suspend fun getLocationsById(id: String): List<LocationInfo> = try {
        rickAndMortyApi.getLocationsById(id)
    } catch (e: Exception) {
        locationsMapper.mapFromDbToInfoList(rickAndMortyDao.getLocationsById(id))
    }

    override suspend fun getAllEpisodes(page: Int, filter: EpisodesFilter) = try {
        val result = rickAndMortyApi.getAllEpisodes(page, filter.name, filter.season)
        rickAndMortyDao.saveAllEpisodes(episodesMapper.mapFromInfoToDbList(result.results))
        result
    } catch (e: Exception) {
        val list = episodesMapper.mapFromDbToInfoList(
            rickAndMortyDao.getAllEpisodes(
                if (filter.name.isNullOrEmpty()) filter.name else "%${filter.name}%",
                if (filter.season.isNullOrEmpty()) filter.season else "%${filter.season}%"
            )
        )
        Episodes(list, InfoResult(1))
    }

    override suspend fun getEpisodeById(id: Int): EpisodeInfo = try {
        rickAndMortyApi.getEpisodeById(id)
    } catch (e: Exception) {
        episodesMapper.mapFromDbToInfo(rickAndMortyDao.getEpisodeById(id))
    }

    override suspend fun getEpisodesById(id: String): List<EpisodeInfo> = try {
        rickAndMortyApi.getEpisodesById(id)
    } catch (e: Exception) {
        episodesMapper.mapFromDbToInfoList(rickAndMortyDao.getEpisodesById(id))
    }
}