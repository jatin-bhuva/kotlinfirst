package com.example.kotliin1.ui.moviedetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.kotliin1.Constants
import com.example.kotliin1.databinding.ItemCastBinding
import com.example.kotliin1.domain.model.Cast

class CastListViewAdapter(
    private val castList: List<Cast>,
    private val onItemClick: (Cast) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = castList.size

    override fun getItem(position: Int): Any = castList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val view: View

        if (convertView == null) {
            val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            view = binding.root
            viewHolder = ViewHolder(binding)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val cast = castList[position]
        with(viewHolder.binding) {
            name.text = cast.name
            character.text = cast.character
            Glide.with(ivPosterImage.context)
                .load("${Constants.IMAGE_BASE_PATH}${cast.profilePath}")
                .into(ivPosterImage)

            root.setOnClickListener {
                onItemClick(cast)
            }
        }

        return view
    }

    private class ViewHolder(val binding: ItemCastBinding)

}
