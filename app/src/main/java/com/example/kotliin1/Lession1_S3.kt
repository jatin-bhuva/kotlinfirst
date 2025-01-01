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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lession1_s3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnTop = findViewById<LinearLayout>(R.id.btnTop);
        val btnCenter = findViewById<LinearLayout>(R.id.view);
        val btnBottom = findViewById<Button>(R.id.btnBottom);
        val L1 = findViewById<LinearLayout>(R.id.linearLayout);
        val L2 = findViewById<LinearLayout>(R.id.linearLayout3);
        val R1 = findViewById<LinearLayout>(R.id.linearLayout2);
        val R2 = findViewById<LinearLayout>(R.id.linearLayout4);
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
}