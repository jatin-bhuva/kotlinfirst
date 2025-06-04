package com.example.kotliin1.domain.repository

import com.example.kotliin1.domain.model.Cast
import com.example.kotliin1.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(page: Int = 1): List<Movie>
    suspend fun getMovieCast(movieId: Int): List<Cast>
}