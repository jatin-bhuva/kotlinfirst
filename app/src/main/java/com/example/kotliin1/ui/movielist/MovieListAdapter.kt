package com.example.kotliin1.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotliin1.databinding.ItemMovieBinding
import com.example.kotliin1.domain.model.Movie

class MovieListAdapter(
    private var movieList: List<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private var originalList: List<Movie> = movieList

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.tvZMovieName.text = movie.title
            binding.tvZDescription.text = movie.overview
            Glide.with(binding.ivPosterImage.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(binding.ivPosterImage)

            binding.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    fun updateList(newList: List<Movie>) {
        originalList = newList
        movieList = newList
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        movieList = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}
