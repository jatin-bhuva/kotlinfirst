package com.example.kotliin1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class Lesson6S1 : AppCompatActivity() {
    private var selectedFolder: String = "/storage/emulated/0/Download"
    private lateinit var imageListView: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lesson6_s1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeVariables()
        displayImages(selectedFolder)
    }

    private fun initializeVariables() {
        imageListView = findViewById(R.id.photo_list)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun displayImages(folderPath: String) {
        val folder = File(folderPath)
        val imageFiles = folder.listFiles { file ->
            file.extension in listOf("jpg", "png", "jpeg")
        }?.toList() ?: emptyList()
        val adapter = ImageAdapter(imageFiles)
        imageListView.layoutManager = GridLayoutManager(this, 3)
        imageListView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, Lesson6S2::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}