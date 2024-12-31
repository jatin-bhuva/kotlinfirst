package com.example.kotliin1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Screen1A1 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_screen1_a1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userInput = findViewById<EditText>(R.id.user_in)
        val email = findViewById<EditText>(R.id.email_in)
        val password = findViewById<EditText>(R.id.pass_in)
        val contactNumber = findViewById<EditText>(R.id.contact_in)
        val nextBtn = findViewById<Button>(R.id.next_btn)

        nextBtn?.setOnClickListener(){
            val valid  = validateInputs(userInput, email, password, contactNumber)
            if(valid){
                Toast.makeText(this, "Validation Successful", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Please add valid details", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun validateInputs(userInput: EditText, email: EditText, password: EditText, contactNumber: EditText): Boolean {
        val userName = userInput.text.toString().trim()
        val emailText = email.text.toString().trim()
        val passwordText = password.text.toString().trim()
        val contactNumberText = contactNumber.text.toString().trim()

        if(userName.isEmpty()){
            userInput.error = "Please enter user name"

        }
         if(emailText.isEmpty()){
            email.error = "Please enter email"
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.error = "Invalid email address"

        }
         if(passwordText.isEmpty() || passwordText.length<8){
            password.error = "Minimum length is 8"

        }
         if(contactNumberText.isEmpty()){
            contactNumber.error = "Contact number is required"

        }
        else if (!contactNumberText.matches("^[0-9]+$".toRegex()) || contactNumberText.length != 10) {
            contactNumber.error = "Invalid contact number"

        }

        return userInput.error.isNullOrBlank() && email.error.isNullOrBlank() && password.error.isNullOrBlank() && contactNumber.error.isNullOrBlank()

    }
}