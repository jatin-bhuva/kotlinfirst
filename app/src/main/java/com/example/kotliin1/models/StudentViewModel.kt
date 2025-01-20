package com.example.kotliin1.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.kotliin1.db.AppDatabase
import com.example.kotliin1.db.Student
import com.example.kotliin1.db.StudentRepository

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: StudentRepository;
    public var studentList: LiveData<List<Student>>

    init {
        val appDB = AppDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(appDB)
        studentList = repository.getAllStudents()
    }
}