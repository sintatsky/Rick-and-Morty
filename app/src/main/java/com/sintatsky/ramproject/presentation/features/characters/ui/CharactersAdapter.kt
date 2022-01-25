package com.sintatsky.ramproject.presentation.features.characters.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sintatsky.ramproject.databinding.CharacterItemBinding
import com.sintatsky.ramproject.domain.entities.CharacterInfo
import com.squareup.picasso.Picasso

class CharactersAdapter :
    ListAdapter<CharacterInfo, CharactersAdapter.CharactersViewHolder>(CharactersDiffCallback()) {

    var onCharacterClickListener: OnCharacterClickListener? = null

    inner class CharactersViewHolder(val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterInfo) {
            with(binding) {
                tvCharacterName.text = character.name
                tvCharacterStatus.text = character.status
                tvCharacterGender.text = character.gender
                tvCharacterSpecies.text = character.species
                Picasso.get()
                    .load(character.image)
                    .into(ivCharacter)
                root.setOnClickListener {
                    onCharacterClickListener?.onCharacterClick(character.id)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = CharacterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CharactersDiffCallback : DiffUtil.ItemCallback<CharacterInfo>() {
        override fun areItemsTheSame(oldItem: CharacterInfo, newItem: CharacterInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterInfo, newItem: CharacterInfo): Boolean {
            return oldItem == newItem
        }
    }

    interface OnCharacterClickListener {
        fun onCharacterClick(characterId: Int)
    }
}