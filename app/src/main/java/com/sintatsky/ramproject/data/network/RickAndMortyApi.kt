package com.sintatsky.ramproject.data.network

import com.sintatsky.ramproject.domain.entities.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("status") status: String?,
        @Query("gender") gender: String?,
        @Query("species") species: String?,
    ): Characters

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterInfo

    @GET("character/{id}")
    suspend fun getCharactersById(
        @Path("id") id: String
    ): List<CharacterInfo>

    @GET("location")
    suspend fun getAllLocations(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("type") type: String?
    ): Locations

    @GET("location/{id}")
    suspend fun getLocationById(
        @Path("id") id: Int
    ): LocationInfo

    @GET("location/{id}")
    suspend fun getLocationsById(
        @Path("id") id: String
    ): List<LocationInfo>

    @GET("episode")
    suspend fun getAllEpisodes(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("episode") season: String?
    ): Episodes

    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: Int
    ): EpisodeInfo

    @GET("episode/{id}")
    suspend fun getEpisodesById(
        @Path("id") id: String
    ): List<EpisodeInfo>
}