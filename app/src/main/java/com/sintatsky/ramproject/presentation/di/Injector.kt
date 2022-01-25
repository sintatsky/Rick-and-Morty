package com.sintatsky.ramproject.presentation.di

import android.content.Context
import com.sintatsky.ramproject.presentation.di.components.AppComponent
import com.sintatsky.ramproject.presentation.di.components.DaggerAppComponent
import com.sintatsky.ramproject.presentation.di.modules.AppModule
import com.sintatsky.ramproject.presentation.features.characters.di.CharacterFragmentComponent
import com.sintatsky.ramproject.presentation.features.characters.di.CharactersFragmentComponent
import com.sintatsky.ramproject.presentation.features.episodes.di.EpisodeFragmentComponent
import com.sintatsky.ramproject.presentation.features.episodes.di.EpisodesFragmentComponent
import com.sintatsky.ramproject.presentation.features.locations.di.LocationFragmentComponent
import com.sintatsky.ramproject.presentation.features.locations.di.LocationsFragmentComponent

object Injector {

    private lateinit var appComponent: AppComponent

    val charactersFragmentComponent: CharactersFragmentComponent
        get() = appComponent.charactersFragmentComponent

    val characterFragmentComponent: CharacterFragmentComponent
        get() = appComponent.characterFragmentComponent

    val episodesFragmentComponent: EpisodesFragmentComponent
        get() = appComponent.episodesFragmentComponent

    val episodeFragmentComponent: EpisodeFragmentComponent
        get() = appComponent.episodeFragmentComponent

    val locationsFragmentComponent: LocationsFragmentComponent
        get() = appComponent.locationsFragmentComponent

    val locationFragmentComponent: LocationFragmentComponent
        get() = appComponent.locationFragmentComponent

    fun createAppComponent(context: Context) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .build()
    }
}