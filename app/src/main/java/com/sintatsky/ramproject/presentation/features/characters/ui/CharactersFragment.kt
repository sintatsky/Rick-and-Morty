package com.sintatsky.ramproject.presentation.features.characters.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.sintatsky.ramproject.R
import com.sintatsky.ramproject.databinding.FragmentCharactersBinding
import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.sintatsky.ramproject.domain.entities.CharactersFilter
import com.sintatsky.ramproject.presentation.di.Injector
import com.sintatsky.ramproject.presentation.features.base.BaseFragment
import com.sintatsky.ramproject.presentation.features.characters.character.CharacterDetailFragment
import com.sintatsky.ramproject.presentation.features.characters.viewmodel.CharactersViewModel

class CharactersFragment : BaseFragment<CharactersViewModel>(),
    CharactersFilterFragment.OnSubmitFilterButtonListener {
    private var _binding: FragmentCharactersBinding? = null
    private val binding: FragmentCharactersBinding
        get() = _binding ?: throw RuntimeException("FragmentCharacterDetailBinding is null")

    private var characters = emptyList<CharacterInfo>()
    private lateinit var charactersAdapter: CharactersAdapter

    var onCharacterItemSelected: OnCharacterItemSelected? = null

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onCharacterItemSelected = requireContext() as OnCharacterItemSelected
        Injector.charactersFragmentComponent.inject(this)
        injectViewModel()
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.search.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyKode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN && keyKode == KeyEvent.KEYCODE_ENTER) {
                    filter.name = binding.search.text.toString()
                    page = 1
                    viewModel.reloadCharacters(filter)
                    return true
                }
                return false
            }
        })
        binding.filterButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, CharactersFilterFragment())
                .addToBackStack(null)
                .commit()
        }
        initRecyclerView()
        observeLiveData()
        if (characters.isNullOrEmpty()) {
            page = 1
            viewModel.loadCharacters(page, filter)
        }
        binding.swipe.setOnRefreshListener {
            viewModel.reloadCharacters(filter)
            binding.swipe.isRefreshing = false
        }
    }

    override fun onSubmitFilter() {
        viewModel.reloadCharacters(filter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        charactersAdapter = CharactersAdapter()
        charactersAdapter.onCharacterClickListener =
            object : CharactersAdapter.OnCharacterClickListener {
                override fun onCharacterClick(characterId: Int) {
                    onCharacterItemSelected?.onCharacterSelect(characterId)
                }
            }
        with(binding) {
            recyclerView.adapter = charactersAdapter
            recyclerView.itemAnimator = null
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!recyclerView.canScrollVertically(1) and (page < pages)) {
                        page += 1
                        viewModel.loadCharacters(page, filter)
                    }
                }
            })
        }
    }

    private fun observeLiveData() {
        viewModel.charactersLiveData.observe(requireActivity(), Observer(::onCharactersReceived))
        viewModel.isErrorLiveData.observe(requireActivity(), { onErrorReceived() })
        viewModel.isLoadingLiveData.observe(requireActivity(), Observer(::onLoadingReceived))
    }

    private fun onCharactersReceived(characters: List<CharacterInfo>) {
        this.characters = characters
        charactersAdapter.submitList(characters)
    }

    private fun onErrorReceived() {
        AlertDialog.Builder(requireContext())
            .setTitle("Failed to load data. Please try to change filter parameters")
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .setPositiveButton("Try again") { _, _ ->
                viewModel.reloadCharacters(filter)
            }
            .show()
        filter = CharactersFilter()
    }

    private fun onLoadingReceived(isLoading: Boolean) {
        binding.progressBar.apply { visibility = if (isLoading) View.VISIBLE else View.GONE }
    }

    interface OnCharacterItemSelected {
        fun onCharacterSelect(characterId: Int)
    }

    companion object {
        var filter = CharactersFilter()
        var page = 1
        var pages = 1
        fun newInstance() = CharactersFragment()
    }
}