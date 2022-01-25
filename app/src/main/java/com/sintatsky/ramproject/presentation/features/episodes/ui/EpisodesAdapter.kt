package com.sintatsky.ramproject.presentation.features.episodes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sintatsky.ramproject.databinding.EpisodeItemBinding
import com.sintatsky.ramproject.domain.entities.EpisodeInfo

class EpisodesAdapter :
    ListAdapter<EpisodeInfo, EpisodesAdapter.EpisodesViewHolder>(EpisodesDiffCallback()) {
    var onEpisodeClickListener: OnEpisodeClickListener? = null

   inner class EpisodesViewHolder(val binding: EpisodeItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(episode: EpisodeInfo){
            with(binding){
                tvEpisodeName.text = "${episode.episode} : ${episode.name}"
                tvEpisodeAirDate.text = episode.date
                root.setOnClickListener {
                    onEpisodeClickListener?.onEpisodeClick(episode.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val binding = EpisodeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EpisodesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EpisodesDiffCallback : DiffUtil.ItemCallback<EpisodeInfo>() {
        override fun areItemsTheSame(oldItem: EpisodeInfo, newItem: EpisodeInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodeInfo, newItem: EpisodeInfo): Boolean {
            return oldItem == newItem
        }
    }

    interface OnEpisodeClickListener {
        fun onEpisodeClick(episodeId: Int)
    }
}