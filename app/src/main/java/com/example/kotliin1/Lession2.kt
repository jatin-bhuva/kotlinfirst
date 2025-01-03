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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Lession2 : AppCompatActivity() {

    private lateinit var lifecycleLog: MutableList<String>
    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var logTextView: TextView
    private  lateinit var nextBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_lession2)
        initializeComponents()
        saveLifeCycleEvent("onCreate")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nextBtn.setOnClickListener{
            val logText = lifecycleLog.joinToString("\n") //
            val i = Intent(applicationContext, Lession2S2::class.java)
            i.putExtra("lifecycleLog", logText)
            startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()
        saveLifeCycleEvent("onStart")
    }

    override fun onResume() {
        super.onResume()
        saveLifeCycleEvent("onResume")
    }

    override fun onPause() {
        super.onPause()
        saveLifeCycleEvent("onPause")
    }

    override fun onStop() {
        super.onStop()
        saveLifeCycleEvent("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        saveLifeCycleEvent("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        saveLifeCycleEvent("onDestroy")
    }

    private fun initializeComponents() {
        lifecycleLog = mutableListOf()
        dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:", Locale.getDefault())
        logTextView = findViewById(R.id.lifeCycleTxt)
        nextBtn = findViewById(R.id.next_btn)
    }

    private fun saveLifeCycleEvent(event: String) {
        val timestamp = dateFormat.format(Date())
        val logEntry = "$event at $timestamp"
        lifecycleLog.add(logEntry)
        updateLifecycleLogTextView()
    }
    private fun updateLifecycleLogTextView() {
       val logText = lifecycleLog.joinToString("\n")
        logTextView.text = logText
    }
}
