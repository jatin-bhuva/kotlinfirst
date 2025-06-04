package com.example.kotliin1.data.api

import com.example.kotliin1.data.model.MovieDtoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.kotliin1.data.model.CastDtoResponse

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MovieDtoResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): CastDtoResponse
}