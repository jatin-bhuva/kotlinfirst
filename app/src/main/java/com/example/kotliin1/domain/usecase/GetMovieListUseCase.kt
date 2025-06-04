package com.example.kotliin1.domain.usecase

import com.example.kotliin1.domain.model.Movie
import com.example.kotliin1.domain.repository.MovieRepository


class GetMovieListUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(page: Int = 1): List<Movie> = repository.getMovies(page)
}