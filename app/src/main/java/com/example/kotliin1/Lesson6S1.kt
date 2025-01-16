package com.example.kotliin1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class Lesson6S1 : BaseActivity() {
    private lateinit  var selectedFolder: String
    private lateinit var imageListView: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var folderTitle: TextView

    private lateinit var sharedPreferences: SharedPreferences

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
        folderTitle = findViewById(R.id.materialTextView)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        getAndSavePreferencesData()
    }

    private fun displayImages(folderPath: String) {
        val folder = File(folderPath)
        val imageFiles = folder.listFiles { file ->
            file.extension in listOf(Constants.JPG, Constants.PNG, Constants.JPEG)
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

    private fun getAndSavePreferencesData() {
        sharedPreferences = getSharedPreferences(Constants.SETTING_PREFS_NAME, MODE_PRIVATE)
        selectedFolder = sharedPreferences.getString(Constants.KEY_SELECTED_FOLDER_PATH, "") ?: ""
        folderTitle.text = selectedFolder
    }

    override fun onResume() {
        getAndSavePreferencesData()
        displayImages(selectedFolder)
        super.onResume()
    }
}