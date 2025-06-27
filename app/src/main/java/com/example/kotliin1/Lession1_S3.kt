package com.example.kotliin1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class Lession1_S3 : AppCompatActivity() {

    private lateinit var  btnTop :LinearLayout
    private lateinit var btnCenter:LinearLayout
    private lateinit var btnBottom :Button
    private lateinit var L1: LinearLayout
    private lateinit var L2 :LinearLayout
    private lateinit var R1 :LinearLayout
    private lateinit var R2 :LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lession1_s3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeVariables()

    }
private fun initializeVariables(){

}

}