package com.example.kotliin1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Lession2S2 : AppCompatActivity() {
    private lateinit var gridView: GridView
    private lateinit var shareBtn: Button
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lession2_s2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        shareBtn = findViewById(R.id.share_button)
        val logText = intent.getStringExtra("lifecycleLog")
        shareBtn.setOnClickListener{
            shareLogText(logText.toString())
        }

        val logEntries = logText?.split("\n") ?: emptyList()

        gridView = findViewById(R.id.gridViewLog)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, logEntries)

        gridView.adapter = adapter
    }

    private fun shareLogText(logText: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, logText)
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

}