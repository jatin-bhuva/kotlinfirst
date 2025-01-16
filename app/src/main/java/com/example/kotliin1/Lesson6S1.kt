package com.example.kotliin1

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        if (isPermissionGranted()) {
            accessMediaFiles()
        } else {
            requestPermission()
        }
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

    private fun isPermissionGranted(): Boolean {
        val permissionStatus = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            100
        )
    }

    private fun accessMediaFiles() {
        Toast.makeText(this, getString(R.string.accessing_photos), Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessMediaFiles()
            } else {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(
                        this,
                        R.string.permission_denied_trying_again,
                        Toast.LENGTH_SHORT
                    ).show()
                    requestPermission()
                } else {
                    Toast.makeText(this, R.string.please_accept_permission, Toast.LENGTH_SHORT)
                        .show()
                    openAppSettings()
                }
            }
        }
    }

    private fun openAppSettings() {
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

}