package com.example.kotliin1.db
import androidx.lifecycle.LiveData
import com.example.kotliin1.StudentDao

class StudentRepository(private val studentDao: StudentDao) {
    suspend fun insertStudent(student: Student) {
        studentDao.insertUser(student)
    }

    suspend fun getAllStudents(): LiveData<List<Student>> {
        return studentDao.getAllStudents()
    }

    suspend fun deleteStudent(student: Student) {
        studentDao.deleteStudent(student)
    }

    suspend fun getStudentById(studentId: Int): Student? {
        return studentDao.getStudentById(studentId)
    }

    suspend fun updateStudent(student: Student) {
        studentDao.updateStudent(student)
    }

}
