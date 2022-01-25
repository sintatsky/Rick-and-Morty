package com.sintatsky.ramproject.presentation.features.locations.di

import com.sintatsky.ramproject.presentation.features.locations.location.LocationDetailFragment
import dagger.Subcomponent

@Subcomponent(modules = [LocationFragmentModule::class])
interface LocationFragmentComponent {
    fun inject(locationDetailFragment: LocationDetailFragment)
}