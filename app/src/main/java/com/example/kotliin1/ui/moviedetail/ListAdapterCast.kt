package com.example.kotliin1.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotliin1.Constants
import com.example.kotliin1.databinding.ItemCastBinding
import com.example.kotliin1.domain.model.Cast

class ListAdapterCast(
    private val onItemClick: (Cast) -> Unit,
) : ListAdapter<Cast, ListAdapterCast.CastViewHolder>(DiffCallback) {
    inner class CastViewHolder(private val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            binding.name.text = cast.name
            binding.character.text = cast.character
            Glide.with(binding.ivPosterImage.context)
                .load("${Constants.IMAGE_BASE_PATH}${cast.profilePath}")
                .into(binding.ivPosterImage)
            binding.root.setOnClickListener {
                onItemClick(cast)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = ItemCastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem == newItem
            }
        }
    }
}