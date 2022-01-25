package com.sintatsky.ramproject.presentation.features.episodes.episode

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.sintatsky.ramproject.R
import com.sintatsky.ramproject.databinding.FragmentEpisodeDetailBinding
import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.sintatsky.ramproject.domain.entities.EpisodeInfo
import com.sintatsky.ramproject.presentation.di.Injector
import com.sintatsky.ramproject.presentation.features.base.BaseFragment
import com.sintatsky.ramproject.presentation.features.characters.character.CharacterDetailFragment

class EpisodeDetailFragment : BaseFragment<EpisodeDetailViewModel>() {
    private var _binding: FragmentEpisodeDetailBinding? = null
    private val binding: FragmentEpisodeDetailBinding
        get() = _binding ?: throw  RuntimeException("FragmentEpisodeDetailBinding is null")

    private var episode: EpisodeInfo? = null
    private var characters: List<CharacterInfo> = emptyList()
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
        Injector.episodeFragmentComponent.inject(this)
        injectViewModel()
        _binding = FragmentEpisodeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (episode == null) {
            val episodeId = arguments?.getInt(EPISODE_ID)
            episode = episodeId?.let { viewModel.loadEpisode(episodeId) }
            episode?.characters?.let { viewModel.loadCharacters(it) }
        }
        with(binding) {
            episodeNumber.text = episode?.episode
            episodeName.text = episode?.name
            episodeAirDate.text = episode?.date
        }
        initRecyclerView()
        observeLiveData()
    }

    private fun initRecyclerView() {
        charactersListAdapter = CharactersListAdapter()
        binding.recyclerViewEpisodeCharacters.adapter = charactersListAdapter
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
                episode?.characters?.let {
                    viewModel.loadCharacters(it)
                }
            }
            .show()
    }

    private fun onLoadingReceived(isLoading: Boolean) {
        binding.progressBar.apply { visibility = if (isLoading) View.VISIBLE else View.GONE }
    }

    interface OnCharacterItemClickListener {
        fun onCharacterClick(characterId: Int)
    }

    companion object {
        private const val EPISODE_ID = "EPISODE_ID"
        fun newInstance(episodeId: Int): EpisodeDetailFragment {
            return EpisodeDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(EPISODE_ID, episodeId)
                }
            }
        }
    }
}