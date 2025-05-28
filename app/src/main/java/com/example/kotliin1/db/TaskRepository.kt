package com.example.kotliin1.db
import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    fun getAllTasks(): LiveData<List<Task>> = taskDao.getAllTasks()
    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }
}
