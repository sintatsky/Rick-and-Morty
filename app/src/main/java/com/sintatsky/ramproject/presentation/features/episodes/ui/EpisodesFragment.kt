package com.sintatsky.ramproject.presentation.features.episodes.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.sintatsky.ramproject.R
import com.sintatsky.ramproject.databinding.FragmentEpisodesBinding
import com.sintatsky.ramproject.domain.entities.EpisodeInfo
import com.sintatsky.ramproject.domain.entities.EpisodesFilter
import com.sintatsky.ramproject.presentation.di.Injector
import com.sintatsky.ramproject.presentation.features.base.BaseFragment
import com.sintatsky.ramproject.presentation.features.episodes.episode.EpisodeDetailFragment
import com.sintatsky.ramproject.presentation.features.episodes.viewmodel.EpisodesViewModel

class EpisodesFragment : BaseFragment<EpisodesViewModel>(),
    EpisodesFilterFragment.OnSubmitFilterButtonListener {
    private var _binding: FragmentEpisodesBinding? = null
    private val binding: FragmentEpisodesBinding
        get() = _binding ?: throw RuntimeException("FragmentEpisodesBinding is null")

    private var episodes = emptyList<EpisodeInfo>()
    private lateinit var episodesAdapter: EpisodesAdapter

    var onEpisodeItemSelected : OnEpisodeItemSelected? = null

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onEpisodeItemSelected = requireContext() as OnEpisodeItemSelected
        Injector.episodesFragmentComponent.inject(this)
        injectViewModel()
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.search.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyKode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN && keyKode == KeyEvent.KEYCODE_ENTER) {
                    filter.name = binding.search.text.toString()
                    page = 1
                    viewModel.reloadEpisodes(filter)
                    return true
                }
                return false
            }
        })
        binding.filterButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, EpisodesFilterFragment())
                .addToBackStack(null)
                .commit()
        }
        initRecyclerView()
        observeLiveData()
        if (episodes.isNullOrEmpty()) {
            page = 1
            viewModel.loadEpisodes(page, filter)
        }
        binding.swipe.setOnRefreshListener {
            viewModel.reloadEpisodes(filter)
            binding.swipe.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        episodesAdapter = EpisodesAdapter()
        episodesAdapter.onEpisodeClickListener =
            object : EpisodesAdapter.OnEpisodeClickListener {
                override fun onEpisodeClick(episodeId: Int) {
                    onEpisodeItemSelected?.onEpisodeSelect(episodeId)
                }
            }
        with(binding) {
            recyclerView.adapter = episodesAdapter
            recyclerView.itemAnimator = null
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1) and (page < pages)) {
                        page += 1
                        viewModel.loadEpisodes(page, filter)
                    }
                }
            })
        }
    }

    private fun observeLiveData() {
        viewModel.episodesLiveData.observe(requireActivity(), Observer(::onEpisodesReceived))
        viewModel.isErrorLiveData.observe(requireActivity(), { onErrorReceived() })
        viewModel.isLoadingLiveData.observe(requireActivity(), Observer(::onLoadingReceived))
    }

    private fun onEpisodesReceived(episodes: List<EpisodeInfo>) {
        this.episodes = episodes
        episodesAdapter.submitList(episodes)
    }

    private fun onErrorReceived() {
        AlertDialog.Builder(requireContext())
            .setTitle("Failed to load data. Please try to change filter parameters")
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .setPositiveButton("Try again") { _, _ ->
                viewModel.reloadEpisodes(filter)
            }
            .show()
        filter = EpisodesFilter()
    }

    private fun onLoadingReceived(isLoading: Boolean) {
        binding.progressBar.apply { visibility = if (isLoading) View.VISIBLE else View.GONE }
    }

    override fun onSubmitFilter() {
        viewModel.reloadEpisodes(filter)
    }

    interface OnEpisodeItemSelected {
        fun onEpisodeSelect(episodeId: Int)
    }

    companion object {
        var filter = EpisodesFilter()
        var page = 1
        var pages = 1
        fun newInstance() = EpisodesFragment()
    }
}