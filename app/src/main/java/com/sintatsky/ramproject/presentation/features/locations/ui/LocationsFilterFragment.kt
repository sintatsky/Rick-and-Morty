package com.sintatsky.ramproject.presentation.features.locations.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sintatsky.ramproject.R
import com.sintatsky.ramproject.databinding.DialogFilterLocationsBinding

class LocationsFilterFragment : BottomSheetDialogFragment() {
    private var _binding: DialogFilterLocationsBinding? = null
    private val binding: DialogFilterLocationsBinding
        get() = _binding ?: throw RuntimeException("DialogFilterLocationsBinding is null")

    var onSubmitLocationsFilterListener: OnSubmitLocationsFilterListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFilterLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            submitFilterButton.setOnClickListener {
                LocationsFragment.filter.apply {
                    name = if (nameFilter.text.isNotEmpty()) nameFilter.text.toString() else null
                    type = if (locationTypeFilter.selectedItem.toString() != "Any") {
                        locationTypeFilter.selectedItem as String
                    } else null
                }
                onSubmitLocationsFilterListener?.submitFilter()
                parentFragmentManager.popBackStack()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, LocationsFragment.newInstance())
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnSubmitLocationsFilterListener {
        fun submitFilter()
    }
}