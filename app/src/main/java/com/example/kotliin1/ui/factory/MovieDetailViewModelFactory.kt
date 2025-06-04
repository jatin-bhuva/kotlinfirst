package com.example.kotliin1.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotliin1.domain.usecase.GetMovieCastUseCase
import com.example.kotliin1.ui.moviedetail.MovieDetailViewModel

class MovieDetailViewModelFactory(
    private val getMovieCastUseCase: GetMovieCastUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(getMovieCastUseCase) as T
    }
}