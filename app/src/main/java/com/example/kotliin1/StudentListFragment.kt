package com.example.kotliin1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotliin1.db.Student
import com.example.kotliin1.models.StudentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class StudentListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var studentAdapter: StudentAdapter
    private val studentViewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeVariables(view)
        getStudentData()
        fabListener()
    }

    private fun getStudentData() {
        studentViewModel.studentList.observe(viewLifecycleOwner) { students ->
            studentAdapter = StudentAdapter(students, ::onEditClicked, ::onDeleteClicked)
            recyclerView.adapter = studentAdapter
        }
    }

    private fun onEditClicked(student: Student) {
        val intent = Intent(requireContext(), Lesson7S2::class.java)
        intent.putExtra(Constants.STUDENT_ID_KEY, student.id)
        startActivity(intent)
    }

    private fun initializeVariables(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        fab = view.findViewById(R.id.fab)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onDeleteClicked(student: Student) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_student)
            .setMessage(R.string.sure_to_delete_student)
            .setPositiveButton(R.string.yes) { _, _ ->
                lifecycleScope.launch {
                    studentViewModel.deleteStudent(student)
                }
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun fabListener() {
        fab.setOnClickListener {
            val intent = Intent(requireContext(), Lesson7S2::class.java)
            startActivity(intent)
        }
    }
}