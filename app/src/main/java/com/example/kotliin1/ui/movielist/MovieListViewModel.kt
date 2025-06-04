package com.example.kotliin1.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotliin1.domain.model.Movie
import com.example.kotliin1.domain.usecase.GetMovieListUseCase
import kotlinx.coroutines.launch

class MovieListViewModel(private val getMovies: GetMovieListUseCase) : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _moviesLoading = MutableLiveData<Boolean>()
    val moviesLoading: LiveData<Boolean> get() = _moviesLoading

    private var currentPage = 1
    private val loadedMovies = mutableListOf<Movie>()

    fun fetchMovies(page: Int = currentPage) {
        _moviesLoading.value = true
        viewModelScope.launch {
            try {
                val newMovies = getMovies(page)
                loadedMovies.addAll(newMovies)
                _movies.value = loadedMovies
                currentPage++
            } catch (e: Exception) {
                // Log the error
                e.printStackTrace()  // or Log.e("MovieListViewModel", "Error fetching movies", e)
            } finally {
                _moviesLoading.value = false
            }
        }
    }

    fun resetMovies() {
        currentPage = 1
        loadedMovies.clear()
        _movies.value = emptyList()
    }
}
