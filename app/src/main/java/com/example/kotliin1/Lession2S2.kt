package com.example.kotliin1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Lession2S2 : AppCompatActivity() {
    private lateinit var activityCycleLog: TextView
    private lateinit var shareBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lession2_s2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityCycleLog = findViewById(R.id.activtyCycleLog)
        shareBtn = findViewById(R.id.share_button)
        val logText = intent.getStringExtra("lifecycleLog")
        activityCycleLog.text = logText
        shareBtn.setOnClickListener{
            shareLogText(logText.toString())
        }
    }

    private fun shareLogText(logText: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, logText)
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

}