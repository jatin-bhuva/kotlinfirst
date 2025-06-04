package com.example.kotliin1.data.model

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String?,
    val poster_path: String?,
    val release_date: String
)
data class MovieDtoResponse(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)