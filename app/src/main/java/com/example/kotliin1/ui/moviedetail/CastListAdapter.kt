package com.example.kotliin1.ui.moviedetail
    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.example.kotliin1.databinding.ItemCastBinding
    import com.example.kotliin1.domain.model.Cast

class CastListAdapter(
        private var castList: List<Cast>,
        private val onItemClick: (Cast) -> Unit
    ) : RecyclerView.Adapter<CastListAdapter.CastViewHolder>() {

        private var originalList: List<Cast> = castList

        inner class CastViewHolder(private val binding: ItemCastBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(cast: Cast) {
                binding.name.text = cast.name
                binding.character.text = cast.character
                Glide.with(binding.ivPosterImage.context)
                    .load("https://image.tmdb.org/t/p/w500${cast.profilePath}")
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
            holder.bind(castList[position])
        }

        override fun getItemCount(): Int = castList.size

        fun updateList(newList: List<Cast>) {
            originalList = newList
            castList = newList
            notifyDataSetChanged()
        }


    }
