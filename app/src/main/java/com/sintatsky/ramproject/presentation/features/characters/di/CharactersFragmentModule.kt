package com.sintatsky.ramproject.presentation.features.characters.di

import androidx.lifecycle.ViewModel
import com.sintatsky.ramproject.presentation.di.annotation.ViewModelKey
import com.sintatsky.ramproject.presentation.features.characters.viewmodel.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CharactersFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    fun bindViewModel(viewModel: CharactersViewModel): ViewModel
}