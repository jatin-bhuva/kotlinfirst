package com.example.kotliin1.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.kotliin1.R

@BindingAdapter("joinedText")
fun setJoinedText(textView: TextView, list: List<String>?) {
    textView.text = list?.joinToString(", ") ?: "N/A"
}
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}