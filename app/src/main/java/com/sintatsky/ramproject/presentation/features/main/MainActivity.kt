package com.sintatsky.ramproject.presentation.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sintatsky.ramproject.R
import com.sintatsky.ramproject.presentation.features.characters.character.CharacterDetailFragment
import com.sintatsky.ramproject.presentation.features.characters.ui.CharactersFragment
import com.sintatsky.ramproject.presentation.features.episodes.episode.EpisodeDetailFragment
import com.sintatsky.ramproject.presentation.features.episodes.ui.EpisodesFragment
import com.sintatsky.ramproject.presentation.features.locations.location.LocationDetailFragment
import com.sintatsky.ramproject.presentation.features.locations.ui.LocationsFragment

class MainActivity : AppCompatActivity(),
    CharactersFragment.OnCharacterItemSelected,
    EpisodesFragment.OnEpisodeItemSelected,
    LocationsFragment.OnLocationItemSelected,
    CharacterDetailFragment.OnLocationItemClickListener,
    EpisodeDetailFragment.OnCharacterItemClickListener,
    LocationDetailFragment.OnCharacterItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, CharactersFragment.newInstance())
            .commit()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.selectedItemId = R.id.bottom_nav_menu_characters

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_menu_characters -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, CharactersFragment.newInstance())
                        .commit()
                    true
                }
                R.id.bottom_nav_menu_locations -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, LocationsFragment.newInstance())
                        .commit()
                    true
                }
                R.id.bottom_nav_menu_episodes -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, EpisodesFragment.newInstance())
                        .commit()
                    true
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, CharactersFragment.newInstance())
                        .commit()
                    true
                }
            }
        }
    }

    override fun onCharacterSelect(characterId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frameContainer,
                CharacterDetailFragment.newInstance(characterId)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onEpisodeSelect(episodeId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frameContainer,
                EpisodeDetailFragment.newInstance(episodeId)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onLocationSelect(locationId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frameContainer,
                LocationDetailFragment.newInstance(locationId)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onLocationClick(locationId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, LocationDetailFragment.newInstance(locationId))
            .addToBackStack(null)
            .commit()
    }

    override fun onCharacterClick(characterId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frameContainer,
                CharacterDetailFragment.newInstance(characterId)
            )
            .addToBackStack(null)
            .commit()
    }
}