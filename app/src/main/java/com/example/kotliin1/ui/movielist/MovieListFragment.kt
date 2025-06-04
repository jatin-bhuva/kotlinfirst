package com.example.kotliin1.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.kotliin1.data.api.MovieApiService
import com.example.kotliin1.data.api.RetrofitMovieHelper

import com.example.kotliin1.data.repository.MovieRepositoryImpl

import com.example.kotliin1.databinding.FragmentMovieListBinding
import com.example.kotliin1.domain.model.Movie
import com.example.kotliin1.domain.usecase.GetMovieListUseCase
import com.example.kotliin1.ui.factory.MovieListViewModelFactory



class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitMovieHelper.getInstance().create(MovieApiService::class.java)
        val repository = MovieRepositoryImpl(api)
        val getMovieListUseCase = GetMovieListUseCase(repository)

        viewModel = ViewModelProvider(
            this,
            MovieListViewModelFactory(getMovieListUseCase)
        ).get(MovieListViewModel::class.java)
        viewModel.fetchMovies()
        val adapter = MovieListAdapter(emptyList()) { movie: Movie ->
            val action = MovieListFragmentDirections
                .actionFragmentTaskListToFragmentMovieDetail(movie)
            findNavController().navigate(action)

        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner, Observer {
           adapter.updateList(it)
        })

        viewModel.moviesLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                return true
            }
        })
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val totalItems = layoutManager.itemCount

                if (lastVisible >= totalItems - 1) {
                    viewModel.fetchMovies()
                }
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
