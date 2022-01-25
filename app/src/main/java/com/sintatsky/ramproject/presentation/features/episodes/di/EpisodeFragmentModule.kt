package com.sintatsky.ramproject.presentation.features.episodes.di

import androidx.lifecycle.ViewModel
import com.sintatsky.ramproject.presentation.di.annotation.ViewModelKey
import com.sintatsky.ramproject.presentation.features.episodes.episode.EpisodeDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EpisodeFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeDetailViewModel::class)
    fun bindViewModel(viewModel: EpisodeDetailViewModel): ViewModel
}