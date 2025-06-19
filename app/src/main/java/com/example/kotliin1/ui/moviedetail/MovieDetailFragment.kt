package com.example.kotliin1.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kotliin1.Constants
import com.example.kotliin1.data.api.MovieApiService
import com.example.kotliin1.data.api.RetrofitMovieHelper
import com.example.kotliin1.data.repository.MovieRepositoryImpl
import com.example.kotliin1.databinding.FragmentMovieDetailsBinding
import com.example.kotliin1.domain.model.Movie
import com.example.kotliin1.domain.usecase.GetMovieCastUseCase
import com.example.kotliin1.ui.factory.MovieDetailViewModelFactory

class MovieDetailFragment : Fragment() {

    private lateinit var _binding: FragmentMovieDetailsBinding
    private val binding get() = _binding

    private val args: MovieDetailFragmentArgs by navArgs()
    //    private lateinit var castAdapter1: CastListViewAdapter
    private lateinit var castListAdapter: ListAdapterCast


    private val viewModel: MovieDetailViewModel by lazy {
        val movieService = RetrofitMovieHelper.getInstance().create(MovieApiService::class.java)
        val repository = MovieRepositoryImpl(movieService)
        val getMovieCastUseCase = GetMovieCastUseCase(repository)
        ViewModelProvider(this, MovieDetailViewModelFactory(getMovieCastUseCase))[MovieDetailViewModel::class.java]
    }

    //    private val castAdapter: CastListAdapter by lazy {
    //        CastListAdapter(emptyList()) {}
    //    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val movie = args.movie

        initializeRecyclerAdapters()
        setInitialData(movie)

        viewModel.fetchCast(movie.id)

        viewModel.cast.observe(viewLifecycleOwner) { castList ->
            castListAdapter.submitList(castList)
           // castAdapter.updateList(castList)
        }
    }

    private fun initializeRecyclerAdapters(){
        //        castAdapter1 = CastListViewAdapter(emptyList()) {}
        //        binding.castListView.adapter = castAdapter1

        //        viewModel.cast.observe(viewLifecycleOwner) { castList ->
        //            castAdapter1 = CastListViewAdapter(castList) {}
        //            binding.castListView.adapter = castAdapter1
        //        }
        //        binding.castRecyclerView.layoutManager = LinearLayoutManager(
        //            requireContext(), LinearLayoutManager.HORIZONTAL, false
        //        )
        //        binding.castRecyclerView.adapter = castAdapter
        castListAdapter = ListAdapterCast{}

        binding.castRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = castListAdapter
        }
    }

    private fun setInitialData(movie: Movie){
        binding.tvName.text = movie.title
        binding.tvZDescription.text = movie.overview ?: "No info"
        binding.releaseDate.text = movie.releaseDate
        Glide.with(requireContext())
            .load("${Constants.IMAGE_BASE_PATH}${movie.posterPath}")
            .into(binding.ivBreedImage)
    }

}
