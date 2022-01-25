package com.sintatsky.ramproject.presentation.features.characters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sintatsky.ramproject.R
import com.sintatsky.ramproject.databinding.DialogFilterCharactersBinding

class CharactersFilterFragment : BottomSheetDialogFragment() {
    private var _binding: DialogFilterCharactersBinding? = null
    private val binding: DialogFilterCharactersBinding
        get() = _binding ?: throw RuntimeException("DialogFilterCharactersBinding is null")

    var onSubmitFilterButtonListener: OnSubmitFilterButtonListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFilterCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            submitFilterButton.setOnClickListener {
                CharactersFragment.filter.apply {
                    name = if (nameFilter.text.isNotEmpty()) nameFilter.text.toString() else null

                    status = if (statusFilter.selectedItem.toString() != "Any") {
                        statusFilter.selectedItem as String
                    } else null

                    gender = if (genderFilter.selectedItem.toString() != "Any") {
                        genderFilter.selectedItem as String
                    } else null

                    species = if (speciesFilter.selectedItem.toString() != "Any") {
                        speciesFilter.selectedItem as String
                    } else null
                }
                onSubmitFilterButtonListener?.onSubmitFilter()
                parentFragmentManager.popBackStack()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, CharactersFragment.newInstance())
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