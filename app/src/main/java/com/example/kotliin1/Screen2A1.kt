package com.example.kotliin1

import android.graphics.Color
import android.os.Build
import android.os.Build.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Screen2A1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

            window.statusBarColor = resources.getColor(android.R.color.white, theme)


        // Make the status bar icons dark (to be visible on a white background)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setContentView(R.layout.activity_screen2_a2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn = findViewById<Button>(R.id.btnAuth)

        val input1 = findViewById<EditText>(R.id.editText1)
        val option1 = findViewById<TextView>(R.id.otp1)
        val option2 = findViewById<TextView>(R.id.otp2)
        val option3 = findViewById<TextView>(R.id.otp3)
        val option4 = findViewById<TextView>(R.id.otp4)

        val optionsSet = listOf(option1, option2, option3, option4)
        for(option in optionsSet){
            option.setOnClickListener(){
                input1.setText(option.text)
            }
        }


    }
}