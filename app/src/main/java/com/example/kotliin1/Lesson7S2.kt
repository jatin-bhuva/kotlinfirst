package com.example.kotliin1

import android.os.Bundle
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.kotliin1.db.AppDatabase
import com.example.kotliin1.db.Student
import com.example.kotliin1.db.StudentDao
import com.example.kotliin1.db.StudentRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.launch

class Lesson7S2 : AppCompatActivity() {

    private lateinit var tilFullName: TextInputLayout
    private lateinit var etFullName: TextInputEditText
    private lateinit var tilEmail: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var tilPhone: TextInputLayout
    private lateinit var etPhone: TextInputEditText
    private lateinit var tvGender: MaterialTextView
    private lateinit var rgGender: RadioGroup
    private lateinit var rbMale: MaterialRadioButton
    private lateinit var rbFemale: MaterialRadioButton
    private lateinit var tilDob: TextInputLayout
    private lateinit var etDob: TextInputEditText
    private lateinit var tilDepartment: TextInputLayout
    private lateinit var actvDepartment: AutoCompleteTextView
    private lateinit var tvInterests: MaterialTextView
    private lateinit var cbSports: MaterialCheckBox
    private lateinit var cbReading: MaterialCheckBox
    private lateinit var cbTraveling: MaterialCheckBox
    private lateinit var btnSubmit: MaterialButton
    private lateinit var departmentAdapter: ArrayAdapter<String>
    private lateinit var db: AppDatabase
    private lateinit var studentDao: StudentDao
    private lateinit var studentRepository: StudentRepository
    private var studentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lesson7_s2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        studentId = intent.getIntExtra(Constants.STUDENT_ID_KEY, -1)
        initializeVariables()
        if (studentId != -1) {
            fetchStudentData(studentId)
        }
    }

    private fun initializeVariables() {
        tilFullName = findViewById(R.id.tilFullName)
        etFullName = findViewById(R.id.etFullName)
        tilEmail = findViewById(R.id.tilEmail)
        etEmail = findViewById(R.id.etEmail)
        tilPhone = findViewById(R.id.tilPhone)
        etPhone = findViewById(R.id.etPhone)
        tvGender = findViewById(R.id.tvGender)
        rgGender = findViewById(R.id.rgGender)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        tilDob = findViewById(R.id.tilDob)
        etDob = findViewById(R.id.etDob)
        tilDepartment = findViewById(R.id.tilDepartment)
        actvDepartment = findViewById(R.id.actvDepartment)
        tvInterests = findViewById(R.id.tvInterests)
        cbSports = findViewById(R.id.cbSport)
        cbReading = findViewById(R.id.cbReading)
        cbTraveling = findViewById(R.id.cbTraveling)
        btnSubmit = findViewById(R.id.btnSubmit)
        db = AppDatabase.getDatabase(this)
        studentDao = db.studentDao()
        studentRepository = StudentRepository(studentDao)

        departmentAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                Constants.DEPARTMENT_LIST
            )
        actvDepartment.run {
            isSaveEnabled = false
        }
        actvDepartment.setAdapter(departmentAdapter)

        btnSubmit.setOnClickListener {
            handleFormSubmission()
        }

    }

    private fun handleFormSubmission() {
        val fullName = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val dob = etDob.text.toString().trim()
        val department = actvDepartment.text.toString().trim()
        val gender = when (rgGender.checkedRadioButtonId) {
            R.id.rbMale -> getString(R.string.male)
            R.id.rbFemale -> getString(R.string.female)
            else -> null
        }
        val interests = mutableListOf<String>().apply {
            when {
                cbSports.isChecked -> add(getString(R.string.sports))
                cbReading.isChecked -> add(getString(R.string.reading))
                cbTraveling.isChecked -> add(getString(R.string.traveling))
            }
        }

        var isValid = true

        when {
            fullName.isEmpty() -> {
                tilFullName.error = getString(R.string.full_name_error)
                isValid = false
            }

            else -> tilFullName.error = null
        }

        when {
            email.isEmpty() -> {
                tilEmail.error = getString(R.string.email_error)
                isValid = false
            }

            !ValidationUtils.isValidEmail(email) -> {
                tilEmail.error = getString(R.string.invalid_email_error)
                isValid = false
            }

            else -> tilEmail.error = null
        }

        when {
            phone.isEmpty() -> {
                tilPhone.error = getString(R.string.phone_error)
                isValid = false
            }

            !ValidationUtils.isValidPhone(phone) -> {
                tilPhone.error = getString(R.string.invalid_phone_error)
                isValid = false
            }

            else -> tilPhone.error = null
        }


        when {
            dob.isEmpty() -> {
                tilDob.error = getString(R.string.dob_error)
                isValid = false
            }

            !ValidationUtils.isValidDate(dob) -> {
                tilDob.error = getString(R.string.invalid_date_error)
                isValid = false
            }

            else -> tilDob.error = null
        }

        when {
            department.isEmpty() -> {
                tilDepartment.error = getString(R.string.department_error)
                isValid = false
            }

            else -> tilDepartment.error = null
        }

        when (gender) {
            null -> {
                Toast.makeText(this, getString(R.string.gender_error), Toast.LENGTH_SHORT).show()
                isValid = false
            }
        }

        when {
            interests.isEmpty() -> {
                Toast.makeText(this, getString(R.string.interests_error), Toast.LENGTH_SHORT).show()
                isValid = false
            }
        }

        if (isValid) {
            val student = Student(
                fullName = fullName,
                email = email,
                phone = phone,
                dob = dob,
                department = department,
                gender = gender ?: getString(R.string.not_specified),
                interests = interests.joinToString(", "),
                id = studentId.takeIf { it > 0 } ?: 0
            )
            addUpdateStudentData(student)
        }
    }

    private fun addUpdateStudentData(student: Student) {
        lifecycleScope.launch {
            when {
                student.id == 0 -> {
                    studentRepository.insertStudent(student)
                    Toast.makeText(this@Lesson7S2, R.string.student_saved, Toast.LENGTH_LONG).show()
                }

                else -> {
                    studentRepository.updateStudent(student)
                    Toast.makeText(this@Lesson7S2, R.string.student_updated, Toast.LENGTH_LONG)
                        .show()
                }
            }
            finish()
        }
    }


    private fun fetchStudentData(studentId: Int) {
        lifecycleScope.launch {
            val student = studentRepository.getStudentById(studentId)
            student?.let {
                etFullName.text = Editable.Factory.getInstance().newEditable(it.fullName);
                etEmail.setText(it.email)
                etPhone.setText(it.phone)
                etDob.setText(it.dob)
                actvDepartment.setText(it.department, false)
                btnSubmit.setText(R.string.update)
                when (it.gender) {
                    getString(R.string.male) -> rbMale.isChecked = true
                    getString(R.string.female) -> rbFemale.isChecked = true
                    else -> {
                        rbMale.isChecked = false
                        rbFemale.isChecked = false
                    }
                }

                val interests = it.interests.split(", ")
                when {
                    getString(R.string.sports) in interests -> cbSports.isChecked = true
                    else -> cbSports.isChecked = false
                }
                when {
                    getString(R.string.reading) in interests -> cbReading.isChecked = true
                    else -> cbReading.isChecked = false
                }
                when {
                    getString(R.string.traveling) in interests -> cbTraveling.isChecked = true
                    else -> cbTraveling.isChecked = false
                }
            }
        }
    }

}