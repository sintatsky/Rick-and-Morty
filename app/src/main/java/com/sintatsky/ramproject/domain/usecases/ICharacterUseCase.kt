package com.sintatsky.ramproject.domain.usecases

import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.sintatsky.ramproject.domain.entities.Characters
import com.sintatsky.ramproject.domain.entities.CharactersFilter

interface ICharacterUseCase {

    suspend fun getAllCharacters(page: Int, filter: CharactersFilter): Characters

    suspend fun getCharacterById(id: Int): CharacterInfo

    suspend fun getCharactersById(id: String): List<CharacterInfo>
}