package com.sintatsky.ramproject.domain.usecases

import com.sintatsky.ramproject.domain.entities.EpisodeInfo
import com.sintatsky.ramproject.domain.entities.Episodes
import com.sintatsky.ramproject.domain.entities.EpisodesFilter
import com.sintatsky.ramproject.domain.repository.Repository
import javax.inject.Inject

class EpisodeUseCase @Inject constructor(
    private val repository: Repository
) : IEpisodeUseCase {
    override suspend fun getAllEpisodes(page: Int, filter: EpisodesFilter): Episodes {
        return repository.getAllEpisodes(page, filter)
    }

    override suspend fun getEpisodeById(id: Int): EpisodeInfo {
        return repository.getEpisodeById(id)
    }

    override suspend fun getEpisodesById(id: String): List<EpisodeInfo> {
        return repository.getEpisodesById(id)
    }
}