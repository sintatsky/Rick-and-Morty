package com.sintatsky.ramproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_db")
data class CharacterDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "species")
    val species: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "origin")
    val origin: String,
    @ColumnInfo(name = "originUrl")
    val originUrl: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "locationUrl")
    val locationUrl: String
)
