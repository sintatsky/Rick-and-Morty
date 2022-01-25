package com.sintatsky.ramproject.presentation.features.characters.character

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.sintatsky.ramproject.databinding.FragmentCharacterDetailBinding
import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.sintatsky.ramproject.domain.entities.EpisodeInfo
import com.sintatsky.ramproject.presentation.di.Injector
import com.sintatsky.ramproject.presentation.features.base.BaseFragment
import com.squareup.picasso.Picasso

class CharacterDetailFragment : BaseFragment<CharacterDetailViewModel>() {
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding: FragmentCharacterDetailBinding
        get() = _binding ?: throw  RuntimeException("FragmentCharacterDetailBinding is null")

    private var episodes: List<EpisodeInfo> = emptyList()
    private var character: CharacterInfo? = null
    private lateinit var episodesListAdapter: EpisodesListAdapter
    var onLocationItemClickListener: OnLocationItemClickListener? = null

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onLocationItemClickListener = requireContext() as OnLocationItemClickListener
        Injector.characterFragmentComponent.inject(this)
        injectViewModel()
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (character == null) {
            val characterId = arguments?.getInt(CHARACTER_ID)
            character = characterId?.let { viewModel.loadCharacter(it) }
            character?.episode?.let { viewModel.loadEpisodes(it) }
        }
        with(binding) {
            characterName.text = character?.name
            characterStatus.text = character?.status
            characterSpecies.text = character?.species
            characterGender.text = character?.gender
            characterType.text =
                if (character?.type?.isEmpty() == true) "unknown" else character?.type
            characterLocation.text = character?.location?.name
            characterLocation.setOnClickListener {
                onLocationItemClickListener?.onLocationClick(
                    character?.location?.url!!.split(
                        "/"
                    ).last().trim().toInt()
                )
            }
            characterOrigin.text = character?.origin?.name
            characterOrigin.setOnClickListener {
                onLocationItemClickListener?.onLocationClick(
                    character?.location?.url!!.split(
                        "/"
                    ).last().trim().toInt()
                )
            }
            Picasso.get()
                .load(character?.image)
                .into(characterAvatar)
        }
        initRecyclerView()
        observeLiveData()
    }

    private fun initRecyclerView() {
        episodesListAdapter = EpisodesListAdapter()
        binding.rvCharacterEpisodes.adapter = episodesListAdapter
        episodesListAdapter.onEpisodeItemClickListener =
            object : EpisodesListAdapter.OnEpisodeItemClickListener {
                override fun onEpisodeItemClick(episodeId: Int) {
                    onLocationItemClickListener?.onLocationClick(episodeId)
                }
            }
    }

    private fun observeLiveData() {
        viewModel.episodeLiveData.observe(requireActivity(), Observer(::onEpisodesReceived))
        viewModel.isErrorLiveData.observe(requireActivity(), { onErrorReceived() })
        viewModel.isLoadingLiveData.observe(requireActivity(), Observer(::onLoadingReceived))
    }

    private fun onEpisodesReceived(episodes: List<EpisodeInfo>) {
        this.episodes = episodes
        episodesListAdapter.submitList(episodes)
    }

    private fun onErrorReceived() {
        AlertDialog.Builder(requireContext())
            .setTitle("Failed to load data. Please try to change filter parameters")
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .setPositiveButton("Try again") { _, _ ->
                character?.episode?.let {
                    viewModel.loadEpisodes(it)
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

    interface OnLocationItemClickListener {
        fun onLocationClick(locationId: Int)
    }

    companion object {
        private const val CHARACTER_ID = "CHARACTER_ID"
        fun newInstance(characterId: Int): CharacterDetailFragment {
            return CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(CHARACTER_ID, characterId)
                }
            }
        }
    }
}