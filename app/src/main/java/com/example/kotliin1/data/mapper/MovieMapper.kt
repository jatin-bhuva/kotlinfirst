package com.example.kotliin1.data.mapper

import com.example.kotliin1.data.model.CastDto
import com.example.kotliin1.data.model.MovieDto
import com.example.kotliin1.domain.model.Cast
import com.example.kotliin1.domain.model.Movie

fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = poster_path,
    releaseDate = release_date
)

fun CastDto.toDomain(): Cast = Cast(
    id = id,
    name = name,
    character = character,
    profilePath = profile_path
)
