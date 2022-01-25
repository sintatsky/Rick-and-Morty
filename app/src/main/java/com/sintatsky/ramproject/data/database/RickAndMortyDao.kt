package com.sintatsky.ramproject.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sintatsky.ramproject.data.model.CharacterDb
import com.sintatsky.ramproject.data.model.EpisodeDb
import com.sintatsky.ramproject.data.model.LocationDb

@Dao
interface RickAndMortyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllCharacters(characters: List<CharacterDb>)

    @Query("SELECT * FROM character_db WHERE (:name IS NULL OR name LIKE :name) AND (:status IS NULL OR status LIKE :status) AND (:gender IS NULL OR gender LIKE :gender) AND (:species IS NULL OR species LIKE :species)")
    suspend fun getAllCharacters(
        name: String?,
        status: String?,
        gender: String?,
        species: String?
    ): List<CharacterDb>

    @Query("SELECT * FROM character_db WHERE id =:id")
    suspend fun getCharacterById(id: Int): CharacterDb

    @Query("SELECT * FROM character_db WHERE id IN (:id)")
    suspend fun getCharactersById(id: String): List<CharacterDb>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllLocations(locations: List<LocationDb>)

    @Query("SELECT * FROM location_db WHERE (:name IS NULL OR name LIKE :name) AND (:type IS NULL OR type LIKE :type)")
    suspend fun getAllLocations(name: String?, type: String?): List<LocationDb>

    @Query("SELECT * FROM location_db WHERE id =:id")
    suspend fun getLocationById(id: Int): LocationDb

    @Query("SELECT * FROM location_db WHERE id IN (:id)")
    suspend fun getLocationsById(id: String): List<LocationDb>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllEpisodes(episodes: List<EpisodeDb>)

    @Query("SELECT * FROM episode_db WHERE (:name IS NULL OR name LIKE :name) AND (:season IS NULL OR episode LIKE :season)")
    suspend fun getAllEpisodes(name: String?, season: String?): List<EpisodeDb>

    @Query("SELECT * FROM episode_db WHERE id =:id")
    suspend fun getEpisodeById(id: Int): EpisodeDb

    @Query("SELECT * FROM episode_db WHERE id IN (:id)")
    suspend fun getEpisodesById(id: String): List<EpisodeDb>
}