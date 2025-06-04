package com.example.kotliin1.data.repository


import com.example.kotliin1.data.api.MovieApiService
import com.example.kotliin1.data.mapper.toDomain
import com.example.kotliin1.domain.model.Movie
import com.example.kotliin1.domain.model.Cast
import com.example.kotliin1.domain.repository.MovieRepository

class MovieRepositoryImpl(private val api: MovieApiService) : MovieRepository {
    override suspend fun getMovies(page: Int): List<Movie> =
        api.getPopularMovies(page).results.map { it.toDomain() }

    override suspend fun getMovieCast(movieId: Int): List<Cast> =
        api.getMovieCredits(movieId).cast.map { it.toDomain() }
}