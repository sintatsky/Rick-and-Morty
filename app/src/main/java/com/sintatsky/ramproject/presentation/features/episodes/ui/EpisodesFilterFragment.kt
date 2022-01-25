package com.sintatsky.ramproject.presentation.features.episodes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sintatsky.ramproject.R
import com.sintatsky.ramproject.databinding.DialogFilterEpisodesBinding

class EpisodesFilterFragment : BottomSheetDialogFragment() {
    private var _binding: DialogFilterEpisodesBinding? = null
    private val binding: DialogFilterEpisodesBinding
        get() = _binding ?: throw RuntimeException("DialogFilterEpisodesBinding is null")

    var onSubmitFilterButtonListener: OnSubmitFilterButtonListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onSubmitFilterButtonListener = requireContext() as OnSubmitFilterButtonListener
        _binding = DialogFilterEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            submitFilterButton.setOnClickListener {
                EpisodesFragment.filter.apply {
                    name = if (nameFilter.text.isNotEmpty()) nameFilter.text.toString() else null
                    season = if (seasonFilter.selectedItem.toString() != "All") {
                        "S0${seasonFilter.selectedItem}"
                    } else null
                }
                onSubmitFilterButtonListener?.onSubmitFilter()
                parentFragmentManager.popBackStack()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, EpisodesFragment.newInstance())
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnSubmitFilterButtonListener {
        fun onSubmitFilter()
    }
}