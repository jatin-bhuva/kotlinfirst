package com.example.kotliin1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotliin1.db.Task

class TaskAdapter(private val onItemClick: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    private var taskList: List<Task> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Task>) {
        taskList = list
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            itemView.findViewById<TextView>(R.id.textTitle).text = task.title
            itemView.findViewById<TextView>(R.id.textDueDate).text = task.dueDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
        holder.itemView.setOnClickListener{
            onItemClick(taskList[position])
        }
    }

    override fun getItemCount(): Int = taskList.size
}
