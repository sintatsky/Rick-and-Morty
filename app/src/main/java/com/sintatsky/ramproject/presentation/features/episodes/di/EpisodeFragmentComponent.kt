package com.sintatsky.ramproject.presentation.features.episodes.di

import com.sintatsky.ramproject.presentation.features.episodes.episode.EpisodeDetailFragment
import dagger.Subcomponent

@Subcomponent(modules = [EpisodeFragmentModule::class])
interface EpisodeFragmentComponent {
    fun inject(episodeDetailFragment: EpisodeDetailFragment)
}