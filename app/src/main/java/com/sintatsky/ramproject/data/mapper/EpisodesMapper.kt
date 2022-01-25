package com.sintatsky.ramproject.data.mapper

import com.sintatsky.ramproject.data.model.EpisodeDb
import com.sintatsky.ramproject.domain.entities.EpisodeInfo
import javax.inject.Inject

class EpisodesMapper @Inject constructor(){

    fun mapFromInfoToDbList(type: List<EpisodeInfo>): List<EpisodeDb>{
        return type.map {
            EpisodeDb(
                id = it.id,
                name = it.name,
                date = it.date,
                episode = it.episode,
                url = it.url
            )
        }
    }

    fun mapFromDbToInfoList(type: List<EpisodeDb>): List<EpisodeInfo>{
        return type.map {
            EpisodeInfo(
                id = it.id,
                name = it.name,
                date = it.date,
                episode = it.episode,
                url = it.url,
                characters = null
            )
        }
    }

    fun mapFromDbToInfo(type: EpisodeDb): EpisodeInfo{
        return EpisodeInfo(
            id = type.id,
            name = type.name,
            date = type.date,
            episode = type.episode,
            url = type.url,
            characters = null
        )
    }
}