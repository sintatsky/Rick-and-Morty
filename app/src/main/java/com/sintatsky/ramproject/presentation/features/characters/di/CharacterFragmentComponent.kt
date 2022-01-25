package com.sintatsky.ramproject.presentation.features.characters.di

import com.sintatsky.ramproject.presentation.features.characters.character.CharacterDetailFragment
import dagger.Subcomponent

@Subcomponent(modules = [CharacterFragmentModule::class])
interface CharacterFragmentComponent {
    fun inject(characterDetailFragment: CharacterDetailFragment)
}