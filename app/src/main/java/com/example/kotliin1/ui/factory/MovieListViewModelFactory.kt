package com.example.kotliin1.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotliin1.domain.usecase.GetMovieListUseCase
import com.example.kotliin1.ui.movielist.MovieListViewModel

class MovieListViewModelFactory(
    private val getMovieListUseCase: GetMovieListUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieListViewModel(getMovieListUseCase) as T
    }
}