package com.sintatsky.ramproject.presentation.features.episodes.di

import com.sintatsky.ramproject.presentation.features.episodes.ui.EpisodesFragment
import dagger.Subcomponent

@Subcomponent(modules = [EpisodesFragmentModule::class])
interface EpisodesFragmentComponent {
    fun inject(episodesFragment: EpisodesFragment)
}