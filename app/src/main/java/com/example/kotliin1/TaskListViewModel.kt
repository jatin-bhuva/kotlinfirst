package com.example.kotliin1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.kotliin1.db.AppDatabase
import com.example.kotliin1.db.Task
import com.example.kotliin1.db.TaskRepository

class TaskListViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = AppDatabase.getDatabase(application).taskDao()
    private val repository = TaskRepository(taskDao)
    val tasks: LiveData<List<Task>> = repository.getAllTasks()
}