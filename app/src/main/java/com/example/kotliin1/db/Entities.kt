package com.example.kotliin1.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kotliin1.Constants

@Entity(tableName = Constants.STUDENT_DB_NAME)
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String,
    val email: String,
    val phone: String,
    val dob: String,
    val department: String,
    val gender: String,
    val interests: String
)
