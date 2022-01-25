package com.sintatsky.ramproject.presentation.di.modules

import android.content.Context
import androidx.room.Room
import com.sintatsky.ramproject.data.database.DatabaseStorage
import com.sintatsky.ramproject.data.repository.RepositoryImpl
import com.sintatsky.ramproject.domain.repository.Repository
import com.sintatsky.ramproject.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.InnerUseCasesModule::class])
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun provideDatabase() =
        Room.databaseBuilder(
            context,
            DatabaseStorage::class.java,
            DatabaseStorage.DATA_BASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideDao(databaseStorage: DatabaseStorage) = databaseStorage.rickAndMortyDao

    @Module
    interface InnerUseCasesModule {

        @Binds
        @Singleton
        fun bindRepository(repository: RepositoryImpl): Repository

        @Singleton
        @Binds
        fun bindCharacterUseCase(characterUseCase: CharacterUseCase): ICharacterUseCase

        @Singleton
        @Binds
        fun bindLocationUseCase(locationUseCase: LocationUseCase): ILocationUseCase

        @Singleton
        @Binds
        fun bindEpisodeUseCase(episodeUseCase: EpisodeUseCase): IEpisodeUseCase
    }
}