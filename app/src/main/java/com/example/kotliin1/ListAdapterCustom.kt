package com.example.kotliin1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ListAdapterCustom: ListAdapter<ListData, ListAdapterCustom.ListViewHolder>(DifUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item  = getItem(position)
        holder.bind(item)
    }

    class ListViewHolder(view: View): RecyclerView.ViewHolder(view){
            val name = view.findViewById<TextView>(R.id.text1)
        fun bind(item: ListData){
            name.text = item.name

        }
    }

    class DifUtil: DiffUtil.ItemCallback<ListData>(){
        override fun areItemsTheSame(
            oldItem: ListData,
            newItem: ListData
        ): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ListData,
            newItem: ListData
        ): Boolean {
           return oldItem == newItem
        }

    }
}