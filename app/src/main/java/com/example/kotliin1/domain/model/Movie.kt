package com.example.kotliin1.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String
):Parcelable
