package com.example.kotliin1.domain.usecase

import com.example.kotliin1.domain.model.Cast
import com.example.kotliin1.domain.repository.MovieRepository


class GetMovieCastUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): List<Cast> = repository.getMovieCast(movieId)
}
