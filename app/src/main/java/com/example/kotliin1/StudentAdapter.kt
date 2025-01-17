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
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        val tvDepartment: TextView = itemView.findViewById(R.id.tv_department)
        val tvGender: TextView = itemView.findViewById(R.id.tv_gender)
        val tvInterests: TextView = itemView.findViewById(R.id.tv_interests)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btn_edit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)
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
