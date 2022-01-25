package com.sintatsky.ramproject.presentation.features.characters.di

import com.sintatsky.ramproject.presentation.features.characters.ui.CharactersFragment
import dagger.Subcomponent

@Subcomponent(modules = [CharactersFragmentModule::class])
interface CharactersFragmentComponent {
    fun inject(charactersFragment: CharactersFragment)
}