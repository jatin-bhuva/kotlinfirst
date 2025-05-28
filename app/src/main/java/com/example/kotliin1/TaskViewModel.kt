package com.example.kotliin1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotliin1.db.AppDatabase
import com.example.kotliin1.db.Task
import com.example.kotliin1.db.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = AppDatabase.getDatabase(application).taskDao()
    private val repository = TaskRepository(taskDao)

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val dueDate = MutableLiveData<String>()
    val repeatInterval = MutableLiveData<String>()
    var taskId: Int? = null
    private val _navigateBack = MutableLiveData<Boolean>()
    val navigateBack: LiveData<Boolean> get() = _navigateBack

    fun saveTask() {
        val task = Task(
            id = taskId ?: 0, // Only if editing
            title = title.value ?: "",
            description = description.value ?: "",
            dueDate = dueDate.value ?: "",
            repeatInterval = repeatInterval.value ?: ""
        )

        viewModelScope.launch {
            if (taskId != null) {
                repository.update(task)
            } else {
                repository.insertTask(task)
            }
            _navigateBack.postValue(true)
        }
    }
    fun loadTask(data: Task){
        title.value = data.title
        description.value  = data.description
        dueDate.value  = data.dueDate
        repeatInterval.value = data.repeatInterval
        taskId = data.id
    }
    fun doneNavigating() {
        _navigateBack.value = false
    }
}


