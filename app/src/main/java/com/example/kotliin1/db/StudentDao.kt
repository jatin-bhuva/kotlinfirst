package com.example.kotliin1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kotliin1.db.Student

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(student: Student)

    @Query("SELECT * FROM ${Constants.STUDENT_DB_NAME}")
    fun getAllStudents(): LiveData<List<Student>>

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * FROM ${Constants.STUDENT_DB_NAME} WHERE id = :studentId")
    suspend fun getStudentById(studentId: Int): Student?

    @Update
    suspend fun updateStudent(student: Student)
}
