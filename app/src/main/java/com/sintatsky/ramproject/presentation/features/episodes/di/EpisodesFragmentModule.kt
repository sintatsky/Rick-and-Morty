package com.sintatsky.ramproject.presentation.features.episodes.di

import androidx.lifecycle.ViewModel
import com.sintatsky.ramproject.presentation.di.annotation.ViewModelKey
import com.sintatsky.ramproject.presentation.features.episodes.viewmodel.EpisodesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EpisodesFragmentModule{

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    fun bindViewModel(viewModel: EpisodesViewModel): ViewModel
}