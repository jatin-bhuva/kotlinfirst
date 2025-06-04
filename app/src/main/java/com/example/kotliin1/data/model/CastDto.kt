package com.example.kotliin1.data.model

data class CastDto(
    val id: Int,
    val name: String,
    val character: String,
    val profile_path: String?
)

data class CastDtoResponse(
    val id: Int,
    val cast: List<CastDto>
)
