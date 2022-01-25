package com.sintatsky.ramproject.presentation.features.episodes.episode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sintatsky.ramproject.databinding.ListItemBinding
import com.sintatsky.ramproject.domain.entities.CharacterInfo

class CharactersListAdapter :
    ListAdapter<CharacterInfo, CharactersListAdapter.CharactersListViewHolder>(
        CharactersListDiffCallback()
    ) {

    var onCharacterItemClickListener: OnCharacterItemClickListener? = null

   inner class CharactersListViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterInfo) {
            binding.text.text = character.name
            binding.root.setOnClickListener {
                onCharacterItemClickListener?.onCharacterItemClick(character.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersListViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharactersListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CharactersListDiffCallback : DiffUtil.ItemCallback<CharacterInfo>() {
        override fun areItemsTheSame(oldItem: CharacterInfo, newItem: CharacterInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterInfo, newItem: CharacterInfo): Boolean {
            return oldItem == newItem
        }
    }

    interface OnCharacterItemClickListener {
        fun onCharacterItemClick(characterId: Int)
    }
}