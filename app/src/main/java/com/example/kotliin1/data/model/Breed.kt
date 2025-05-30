package com.example.kotliin1.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Breed(
    val id: Int,
    val name: String,
    val temperament: String?,
    val life_span: String?,
    val image: Image?
) : Parcelable

@Parcelize
data class Image(
    val url: String?
) : Parcelable
