package com.sintatsky.ramproject.presentation.features.locations.di

import androidx.lifecycle.ViewModel
import com.sintatsky.ramproject.presentation.di.annotation.ViewModelKey
import com.sintatsky.ramproject.presentation.features.locations.viewmodel.LocationsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LocationsFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    fun bindViewModel(viewModel: LocationsViewModel): ViewModel
}