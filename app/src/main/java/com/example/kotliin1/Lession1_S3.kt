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

        btnTop.setOnClickListener {
            L2.visibility = if (L2.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            R2.visibility =  if (R2.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        btnCenter.setOnClickListener{
            L1.visibility = View.VISIBLE;
            L2.visibility = View.VISIBLE;
            R1.visibility = View.VISIBLE;
            R2.visibility = View.VISIBLE
        }
        btnBottom.setOnClickListener{
            L1.visibility = if(L1.visibility == View.VISIBLE) View.GONE else View.VISIBLE;
            R1.visibility = if(R1.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }

    private fun initializeVariables(){
         btnTop = findViewById(R.id.btnTop);
         btnCenter = findViewById(R.id.view);
         btnBottom = findViewById(R.id.btnBottom);
         L1 = findViewById(R.id.linearLayout);
         L2 = findViewById(R.id.linearLayout3);
         R1 = findViewById(R.id.linearLayout2);
         R2 = findViewById(R.id.linearLayout4);
    }
}