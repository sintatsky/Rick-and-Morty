package com.sintatsky.ramproject.presentation.features.characters.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sintatsky.ramproject.databinding.ListItemBinding
import com.sintatsky.ramproject.domain.entities.EpisodeInfo

class EpisodesListAdapter :
    ListAdapter<EpisodeInfo, EpisodesListAdapter.EpisodesListViewHolder>(EpisodesListDiffCallback()) {

    var onEpisodeItemClickListener: OnEpisodeItemClickListener? = null

    inner class EpisodesListViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: EpisodeInfo) {
            binding.text.text = "${episode.episode}: ${episode.name}"
            binding.root.setOnClickListener {
                onEpisodeItemClickListener?.onEpisodeItemClick(episode.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesListViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EpisodesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EpisodesListDiffCallback : DiffUtil.ItemCallback<EpisodeInfo>() {
        override fun areItemsTheSame(oldItem: EpisodeInfo, newItem: EpisodeInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodeInfo, newItem: EpisodeInfo): Boolean {
            return oldItem == newItem
        }
    }

    interface OnEpisodeItemClickListener {
        fun onEpisodeItemClick(episodeId: Int)
    }
}