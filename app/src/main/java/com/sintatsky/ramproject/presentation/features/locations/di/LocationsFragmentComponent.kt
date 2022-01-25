package com.sintatsky.ramproject.presentation.features.locations.di

import com.sintatsky.ramproject.presentation.features.locations.ui.LocationsFragment
import dagger.Subcomponent

@Subcomponent(modules = [LocationsFragmentModule::class])
interface LocationsFragmentComponent {
    fun inject(locationsFragment: LocationsFragment)
}