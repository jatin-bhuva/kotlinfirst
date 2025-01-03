package com.example.kotliin1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Screen1A1 : AppCompatActivity() {

    private lateinit var userInput :EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var contactNumber: EditText
    private lateinit var nextBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_screen1_a1)
        initializeVariables()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nextBtn.setOnClickListener(){
            val valid  = validateInputs(userInput, email, password, contactNumber)
            if(valid){
                Toast.makeText(this, R.string.validation_success, Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(this, R.string.validation_fail, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateInputs(userInput: EditText, email: EditText, password: EditText, contactNumber: EditText): Boolean {
        val userName = userInput.text.toString().trim()
        val emailText = email.text.toString().trim()
        val passwordText = password.text.toString().trim()
        val contactNumberText = contactNumber.text.toString().trim()

        if(userName.isEmpty()){
            userInput.error = getString(R.string.enter_user_name)
        }
         if(emailText.isEmpty()){
            email.error = getString(R.string.enter_email)
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.error = getString(R.string.invalid_email)
        }
         if(passwordText.isEmpty() || passwordText.length<8){
            password.error = getString(R.string.pass_length)
        }
         if(contactNumberText.isEmpty()){
            contactNumber.error = getString(R.string.enter_contact)
        }
        else if (!contactNumberText.matches("^[0-9]+$".toRegex()) || contactNumberText.length != 10) {
            contactNumber.error = getString(R.string.invalid_contact)
        }
        return userInput.error.isNullOrBlank() && email.error.isNullOrBlank() && password.error.isNullOrBlank() && contactNumber.error.isNullOrBlank()
    }

    private fun initializeVariables(){
         userInput = findViewById(R.id.user_in)
         email = findViewById(R.id.email_in)
         password = findViewById(R.id.pass_in)
         contactNumber = findViewById(R.id.contact_in)
         nextBtn = findViewById(R.id.next_btn)
    }
}