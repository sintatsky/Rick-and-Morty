package com.sintatsky.ramproject.domain.entities

import com.google.gson.annotations.SerializedName

data class EpisodeInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val date: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("characters")
    val characters: List<String>?
)
