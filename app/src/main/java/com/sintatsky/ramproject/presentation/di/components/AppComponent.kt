package com.sintatsky.ramproject.presentation.di.components

import android.content.Context
import com.sintatsky.ramproject.presentation.di.modules.AppModule
import com.sintatsky.ramproject.presentation.di.modules.NetworkModule
import com.sintatsky.ramproject.presentation.di.modules.ViewModelFactoryModule
import com.sintatsky.ramproject.presentation.features.characters.di.CharacterFragmentComponent
import com.sintatsky.ramproject.presentation.features.characters.di.CharactersFragmentComponent
import com.sintatsky.ramproject.presentation.features.episodes.di.EpisodeFragmentComponent
import com.sintatsky.ramproject.presentation.features.episodes.di.EpisodesFragmentComponent
import com.sintatsky.ramproject.presentation.features.locations.di.LocationFragmentComponent
import com.sintatsky.ramproject.presentation.features.locations.di.LocationsFragmentComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    val context: Context

    val charactersFragmentComponent: CharactersFragmentComponent
    val characterFragmentComponent: CharacterFragmentComponent

    val episodesFragmentComponent: EpisodesFragmentComponent
    val episodeFragmentComponent: EpisodeFragmentComponent

    val locationsFragmentComponent: LocationsFragmentComponent
    val locationFragmentComponent: LocationFragmentComponent
}