package com.example.kotliin1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.kotliin1.databinding.ActivityLession1S3Binding
import com.example.kotliin1.ui.LoginAction
import com.example.kotliin1.ui.LoginViewModel

class Lession1_S3 : AppCompatActivity() {

    private lateinit var binding: ActivityLession1S3Binding

    private lateinit var viewModel: LoginViewModel
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var submitBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLession1S3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        initializeVariables()
        viewModelObserver()
        eventListener()
    }

    private fun initializeVariables(){
        email = findViewById<EditText>(R.id.email)
        password = findViewById<EditText>(R.id.password)
        submitBtn = findViewById<Button>(R.id.login)
        progressBar = findViewById<ProgressBar>(R.id.progress_circular)
        errorText = findViewById<TextView>(R.id.error)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.uiState.observe(this) { state ->
            binding.state = state
        }

    }

    private fun viewModelObserver() {
        viewModel.uiState.observe(this) { state ->

            Log.d("Test", state.toString())
            if (email.text.toString() != state.email) {
                email.setText(state.email)
            }
            if (password.text.toString() != state.passWord) {
                password.setText(state.passWord)
            }
            progressBar.visibility = if (state.isLoading) View.VISIBLE else View.INVISIBLE
            if (state.loginSuccess) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun eventListener() {

        email.addTextChangedListener {
            viewModel.handleIntent(LoginAction.EmailChanged(it.toString()))
        }

        password.addTextChangedListener {
            viewModel.handleIntent(LoginAction.PasswordChanged(it.toString()))
        }

        submitBtn.setOnClickListener {
            viewModel.handleIntent(LoginAction.SubmitLogin)
        }
    }
}