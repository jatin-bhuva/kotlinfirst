package com.example.kotliin1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotliin1.db.Student

class StudentAdapter(
    private val students: List<Student>,
    private val onEditClicked: (Student) -> Unit,
    private val onDeleteClicked: (Student) -> Unit
) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val tvDepartment: TextView = itemView.findViewById(R.id.tvDepartment)
        val tvGender: TextView = itemView.findViewById(R.id.tvGender)
        val tvInterests: TextView = itemView.findViewById(R.id.tvInterests)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvName.text = student.fullName
        holder.tvEmail.text = student.email
        holder.tvPhone.text = student.phone
        holder.tvDepartment.text = student.department
        holder.tvGender.text = student.gender
        holder.tvInterests.text = student.interests
        holder.btnEdit.setOnClickListener {
            onEditClicked(student)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClicked(student)
        }
    }

    override fun getItemCount(): Int = students.size
}
