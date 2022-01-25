package com.sintatsky.ramproject.presentation.di.modules

import androidx.lifecycle.ViewModelProvider
import com.sintatsky.ramproject.presentation.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}