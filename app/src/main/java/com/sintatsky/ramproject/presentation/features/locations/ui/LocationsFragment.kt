package com.sintatsky.ramproject.presentation.features.locations.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.sintatsky.ramproject.R
import com.sintatsky.ramproject.databinding.FragmentLocationsBinding
import com.sintatsky.ramproject.domain.entities.LocationFilter
import com.sintatsky.ramproject.domain.entities.LocationInfo
import com.sintatsky.ramproject.presentation.di.Injector
import com.sintatsky.ramproject.presentation.features.base.BaseFragment
import com.sintatsky.ramproject.presentation.features.locations.viewmodel.LocationsViewModel

class LocationsFragment : BaseFragment<LocationsViewModel>(),
    LocationsFilterFragment.OnSubmitLocationsFilterListener {
    private var _binding: FragmentLocationsBinding? = null
    private val binding: FragmentLocationsBinding
        get() = _binding ?: throw RuntimeException("FragmentLocationsBinding is null")

    private var locations = emptyList<LocationInfo>()
    private lateinit var locationsAdapter: LocationsAdapter

    var onLocationItemSelected: OnLocationItemSelected? = null

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onLocationItemSelected = requireContext() as OnLocationItemSelected
        Injector.locationsFragmentComponent.inject(this)
        injectViewModel()
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.search.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyKode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN && keyKode == KeyEvent.KEYCODE_ENTER) {
                    filter.name = binding.search.text.toString()
                    page = 1
                    viewModel.reloadLocations(filter)
                    return true
                }
                return false
            }
        })
        binding.filterButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, LocationsFilterFragment())
                .addToBackStack(null)
                .commit()
        }
        initRecyclerView()
        observeLiveData()
        if (locations.isNullOrEmpty()) {
            page = 1
            viewModel.loadLocations(page, filter)
        }
        binding.swipe.setOnRefreshListener {
            viewModel.reloadLocations(filter)
            binding.swipe.isRefreshing = false
        }
    }


    override fun submitFilter() {
        viewModel.reloadLocations(filter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        locationsAdapter = LocationsAdapter()
        locationsAdapter.onLocationItemClickListener =
            object : LocationsAdapter.OnLocationItemClickListener {
                override fun onLocationClick(locationId: Int) {
                    onLocationItemSelected?.onLocationSelect(locationId)
                }
            }
        with(binding) {
            recyclerView.adapter = locationsAdapter
            recyclerView.itemAnimator = null
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!recyclerView.canScrollVertically(1) and (page < pages)) {
                        page += 1
                        viewModel.loadLocations(page, filter)
                    }
                }
            })
        }
    }

    private fun observeLiveData() {
        viewModel.locationsLiveData.observe(requireActivity(), Observer(::onLocationsReceived))
        viewModel.isErrorLiveData.observe(requireActivity(), { onErrorReceived() })
        viewModel.isLoadingLiveData.observe(requireActivity(), Observer(::onLoadingReceived))
    }

    private fun onLocationsReceived(locations: List<LocationInfo>) {
        this.locations = locations
        locationsAdapter.submitList(locations)
    }

    private fun onErrorReceived() {
        AlertDialog.Builder(requireContext())
            .setTitle("Failed to load data. Please try to change filter parameters")
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .setPositiveButton("Try again") { _, _ ->
                viewModel.reloadLocations(filter)
            }
            .show()
        filter = LocationFilter()
    }

    private fun onLoadingReceived(isLoading: Boolean) {
        binding.progressBar.apply { visibility = if (isLoading) View.VISIBLE else View.GONE }
    }

    interface OnLocationItemSelected {
        fun onLocationSelect(locationId: Int)
    }

    companion object {
        var filter = LocationFilter()
        var page = 1
        var pages = 1
        fun newInstance() = LocationsFragment()
    }
}