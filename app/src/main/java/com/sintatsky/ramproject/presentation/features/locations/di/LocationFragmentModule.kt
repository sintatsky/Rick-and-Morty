package com.sintatsky.ramproject.presentation.features.locations.di

import androidx.lifecycle.ViewModel
import com.sintatsky.ramproject.presentation.di.annotation.ViewModelKey
import com.sintatsky.ramproject.presentation.features.locations.location.LocationDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LocationFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailViewModel::class)
    fun bindViewModel(viewModel: LocationDetailViewModel): ViewModel
}