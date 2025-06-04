package com.example.kotliin1.ui.moviedetail
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kotliin1.data.api.MovieApiService
import com.example.kotliin1.data.api.RetrofitMovieHelper
import com.example.kotliin1.data.repository.MovieRepositoryImpl
import com.example.kotliin1.databinding.FragmentMovieDetailsBinding
import com.example.kotliin1.domain.usecase.GetMovieCastUseCase

import com.example.kotliin1.ui.factory.MovieDetailViewModelFactory



class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailFragmentArgs by navArgs()
    private lateinit var viewModel: MovieDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        super.onViewCreated(view, savedInstanceState)
        val movieService = RetrofitMovieHelper.getInstance().create(MovieApiService::class.java)
        val repository = MovieRepositoryImpl(movieService)
        val getMovieListUseCase = GetMovieCastUseCase(repository)
        viewModel = ViewModelProvider(this, MovieDetailViewModelFactory(getMovieListUseCase))[MovieDetailViewModel::class.java]

        val movie = args.movie

        binding.tvName.text = movie.title
        binding.tvZDescription.text = movie.overview ?: "No info"
        binding.releaseDate.text = movie.releaseDate

        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(binding.ivBreedImage)

        viewModel.fetchCast(movie.id)
        val adapter = CastListAdapter(emptyList()) {}

        binding.castRecyclerView.layoutManager =   LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.castRecyclerView.adapter = adapter
        viewModel.cast.observe(viewLifecycleOwner) { castList ->
            adapter.updateList(castList)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

