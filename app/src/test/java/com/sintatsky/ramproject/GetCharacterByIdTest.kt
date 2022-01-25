package com.sintatsky.ramproject

import android.accounts.NetworkErrorException
import com.sintatsky.ramproject.data.database.RickAndMortyDao
import com.sintatsky.ramproject.data.mapper.CharactersMapper
import com.sintatsky.ramproject.data.mapper.EpisodesMapper
import com.sintatsky.ramproject.data.mapper.LocationsMapper
import com.sintatsky.ramproject.data.model.CharacterDb
import com.sintatsky.ramproject.data.network.RickAndMortyApi
import com.sintatsky.ramproject.data.repository.RepositoryImpl
import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.sintatsky.ramproject.domain.entities.CharacterLocation
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class GetCharacterByIdTest {
    @Test
    fun `get character by id with network without database`() {
        runBlocking {
            val api = mockk<RickAndMortyApi> {
                coEvery {
                    getCharacterById(FAKE_CHARACTER_ID)
                } returns character
            }

            val dao = mockk<RickAndMortyDao>()
            val charactersMapper = mockk<CharactersMapper>()
            val locationsMapper = mockk<LocationsMapper>()
            val episodesMapper = mockk<EpisodesMapper>()

            val repository = RepositoryImpl(dao, api, charactersMapper, locationsMapper, episodesMapper)

            repository.getCharacterById(FAKE_CHARACTER_ID)

            coVerify(exactly = 1) {
                api.getCharacterById(FAKE_CHARACTER_ID)
                dao wasNot Called
            }
        }
    }

    @Test
    fun `get character by id from database without network`() {
        runBlocking {

            val charactersMapper = mockk<CharactersMapper>{
               every {
                   mapFromDbToInfo(characterDb)
               } returns character
            }

            val locationsMapper = mockk<LocationsMapper>()
            val episodesMapper = mockk<EpisodesMapper>()


            val api = mockk<RickAndMortyApi> {
                coEvery {
                    getCharacterById(FAKE_CHARACTER_ID)
                } throws NetworkErrorException()
            }

            val dao = mockk<RickAndMortyDao> {
                coEvery {
                    getCharacterById(FAKE_CHARACTER_ID)
                } returns characterDb
            }


            val repository = RepositoryImpl(dao, api, charactersMapper, locationsMapper, episodesMapper)

            repository.getCharacterById(FAKE_CHARACTER_ID)

            coVerify(exactly = 1) {
                api.getCharacterById(FAKE_CHARACTER_ID)
                dao.getCharacterById(FAKE_CHARACTER_ID)
            }
        }
    }

    private val character = CharacterInfo(
        FAKE_CHARACTER_ID, "", "", "",
        "", "", "",
        CharacterLocation("", ""),
        CharacterLocation("", ""),
        listOf()
    )

    private val characterDb = CharacterDb(
        FAKE_CHARACTER_ID, "", "", "",
        "", "", "", "",
        "", "", ""
    )

    companion object {
        private const val FAKE_CHARACTER_ID = 111
    }
}