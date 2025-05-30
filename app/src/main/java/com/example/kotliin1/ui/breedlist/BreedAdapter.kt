package com.example.kotliin1.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotliin1.data.model.Breed
import com.example.kotliin1.databinding.ItemBreedBinding

class BreedListAdapter(
    private var breedList: List<Breed>,
    private val onItemClick: (Breed) -> Unit
) : RecyclerView.Adapter<BreedListAdapter.BreedViewHolder>() {

    private var originalList: List<Breed> = breedList

    inner class BreedViewHolder(private val binding: ItemBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: Breed) {
            binding.tvBreedName.text = breed.name
            Glide.with(binding.ivBreedImage.context)
                .load(breed.image?.url)
                .into(binding.ivBreedImage)

            binding.root.setOnClickListener {
                onItemClick(breed)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val binding = ItemBreedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(breedList[position])
    }

    override fun getItemCount(): Int = breedList.size

    fun updateList(newList: List<Breed>) {
        originalList = newList
        breedList = newList
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        breedList = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}
