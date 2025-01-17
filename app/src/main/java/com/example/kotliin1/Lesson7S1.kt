package com.example.kotliin1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotliin1.db.Student
import com.example.kotliin1.db.StudentRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class Lesson7S1 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var studentDao: StudentDao
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentRepository: StudentRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lesson7_s1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeVariables()
        fabButtonListener()
        getStudentData()
    }

    private fun initializeVariables() {
        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.fab)
        val db = AppDatabase.getDatabase(this)
        studentDao = db.studentDao()
        recyclerView.layoutManager = LinearLayoutManager(this)
        studentRepository = StudentRepository(studentDao)
    }

    private fun fabButtonListener() {
        fab.setOnClickListener {
            val intent = Intent(this, Lesson7S2::class.java)
            startActivity(intent)
        }
    }

    private fun onDeleteClicked(student: Student) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_student)
            .setMessage(R.string.sure_to_delete_student)
            .setPositiveButton(R.string.yes) { _, _ ->
                lifecycleScope.launch {
                    studentRepository.deleteStudent(student)
                }
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun onEditClicked(student: Student) {
        val intent = Intent(this, Lesson7S2::class.java)
        intent.putExtra(Constants.STUDENT_ID_KEY, student.id)
        startActivity(intent)
    }

    private fun getStudentData(){
        studentDao.getAllStudents().observe(this, Observer { students ->
            studentAdapter = StudentAdapter(students, ::onEditClicked, ::onDeleteClicked)
            recyclerView.adapter = studentAdapter
        })
    }

}