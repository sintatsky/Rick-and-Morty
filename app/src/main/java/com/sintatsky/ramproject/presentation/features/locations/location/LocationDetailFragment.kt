package com.sintatsky.ramproject.presentation.features.locations.location

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.sintatsky.ramproject.R
import com.sintatsky.ramproject.databinding.FragmentLocationDetailBinding
import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.sintatsky.ramproject.domain.entities.LocationInfo
import com.sintatsky.ramproject.presentation.di.Injector
import com.sintatsky.ramproject.presentation.features.base.BaseFragment
import com.sintatsky.ramproject.presentation.features.characters.character.CharacterDetailFragment
import com.sintatsky.ramproject.presentation.features.episodes.episode.EpisodeDetailFragment

class LocationDetailFragment : BaseFragment<LocationDetailViewModel>() {
    private var _binding: FragmentLocationDetailBinding? = null
    private val binding: FragmentLocationDetailBinding
        get() = _binding ?: throw  RuntimeException("FragmentLocationDetailBinding is null")

    private var characters: List<CharacterInfo> = emptyList()
    private var location: LocationInfo? = null
    private lateinit var charactersListAdapter: CharactersListAdapter
    var onCharacterItemClickListener : OnCharacterItemClickListener? = null

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onCharacterItemClickListener = requireContext() as OnCharacterItemClickListener
        Injector.locationFragmentComponent.inject(this)
        injectViewModel()
        _binding = FragmentLocationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (location == null) {
            val locationId = arguments?.getInt(LOCATION_ID)
            location = locationId?.let { viewModel.loadLocation(locationId) }
            location?.characters?.let { viewModel.loadCharacters(it) }
        }
        with(binding){
            locationDetail.text = location?.name
            locationDemansion.text = location?.dimension
        }
        initRecyclerView()
        observeLiveData()
    }

    private fun initRecyclerView() {
        charactersListAdapter = CharactersListAdapter()
        binding.recyclerViewCharacters.adapter = charactersListAdapter
        charactersListAdapter.onCharacterItemClickListener =
            object : CharactersListAdapter.OnCharacterItemClickListener {
                override fun onCharacterItemClick(characterId: Int) {
                   onCharacterItemClickListener?.onCharacterClick(characterId)
                }
            }
    }

    private fun observeLiveData() {
        viewModel.charactersLiveData.observe(requireActivity(), Observer(::onCharactersReceived))
        viewModel.isErrorLiveData.observe(requireActivity(), { onErrorReceived() })
        viewModel.isLoadingLiveData.observe(requireActivity(), Observer(::onLoadingReceived))
    }

    private fun onCharactersReceived(characters: List<CharacterInfo>) {
        this.characters = characters
        charactersListAdapter.submitList(characters)
    }

    private fun onErrorReceived() {
        AlertDialog.Builder(requireContext())
            .setTitle("Failed to load data. Please try to change filter parameters")
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .setPositiveButton("Try again") { _, _ ->
                location?.characters?.let {
                    viewModel.loadCharacters(it)
                }
            }
            .show()
    }

    private fun onLoadingReceived(isLoading: Boolean) {
        binding.progressBar.apply { visibility = if (isLoading) View.VISIBLE else View.GONE }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnCharacterItemClickListener {
        fun onCharacterClick(characterId: Int)
    }

    companion object {
        private const val LOCATION_ID = "LOCATION_ID"
        fun newInstance(locationId: Int): LocationDetailFragment {
            return LocationDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(LOCATION_ID, locationId)
                }
            }
        }
    }
}