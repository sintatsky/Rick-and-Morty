package com.sintatsky.ramproject.domain.entities

import com.google.gson.annotations.SerializedName

data class Characters(
    @field:SerializedName("results")
    val results: List<CharacterInfo>,
    @field:SerializedName("info")
    val info: InfoResult
)

data class Locations(
    @field:SerializedName("results")
    val results: List<LocationInfo>,
    @field:SerializedName("info")
    val info: InfoResult
)

data class Episodes(
    @field:SerializedName("results")
    val results: List<EpisodeInfo>,
    @field:SerializedName("info")
    val info: InfoResult
)

data class InfoResult(
    @field:SerializedName("pages")
    val pages: Int
)