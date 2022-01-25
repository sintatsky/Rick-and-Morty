package com.sintatsky.ramproject.domain.usecases

import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.sintatsky.ramproject.domain.entities.Characters
import com.sintatsky.ramproject.domain.entities.CharactersFilter
import com.sintatsky.ramproject.domain.repository.Repository
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val repository: Repository
) : ICharacterUseCase {
    override suspend fun getAllCharacters(page: Int, filter: CharactersFilter): Characters {
        return repository.getAllCharacters(page, filter)
    }

    override suspend fun getCharacterById(id: Int): CharacterInfo {
        return repository.getCharacterById(id)
    }

    override suspend fun getCharactersById(id: String): List<CharacterInfo> {
        return repository.getCharactersById(id)
    }
}