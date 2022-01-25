package com.sintatsky.ramproject.domain.usecases

import com.sintatsky.ramproject.domain.entities.EpisodeInfo
import com.sintatsky.ramproject.domain.entities.Episodes
import com.sintatsky.ramproject.domain.entities.EpisodesFilter

interface IEpisodeUseCase {

    suspend fun getAllEpisodes(page: Int, filter: EpisodesFilter): Episodes

    suspend fun getEpisodeById(id: Int): EpisodeInfo

    suspend fun getEpisodesById(id: String): List<EpisodeInfo>
}