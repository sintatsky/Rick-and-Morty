package com.sintatsky.ramproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sintatsky.ramproject.data.model.CharacterDb
import com.sintatsky.ramproject.data.model.EpisodeDb
import com.sintatsky.ramproject.data.model.LocationDb

@Database(
    entities = [CharacterDb::class, LocationDb::class, EpisodeDb::class],
    version = 1
)
abstract class DatabaseStorage : RoomDatabase() {
    abstract val rickAndMortyDao: RickAndMortyDao

    companion object {
        const val DATA_BASE_NAME = "db.ram"
    }
}