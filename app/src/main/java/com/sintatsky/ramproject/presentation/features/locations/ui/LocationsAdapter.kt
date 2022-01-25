package com.sintatsky.ramproject.presentation.features.locations.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sintatsky.ramproject.databinding.LocationItemBinding
import com.sintatsky.ramproject.domain.entities.LocationInfo

class LocationsAdapter :
    ListAdapter<LocationInfo, LocationsAdapter.LocationsViewHolder>(LocationsDiffCallback()) {

    var onLocationItemClickListener: OnLocationItemClickListener? = null

   inner class LocationsViewHolder(val binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: LocationInfo) {
            with(binding) {
                tvLocationName.text = location.name
                tvLocationDimension.text = location.dimension
                root.setOnClickListener {
                    onLocationItemClickListener?.onLocationClick(location.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val binding = LocationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LocationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LocationsDiffCallback : DiffUtil.ItemCallback<LocationInfo>() {
        override fun areItemsTheSame(oldItem: LocationInfo, newItem: LocationInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LocationInfo, newItem: LocationInfo): Boolean {
            return oldItem == newItem
        }
    }

    interface OnLocationItemClickListener {
        fun onLocationClick(locationId: Int)
    }
}