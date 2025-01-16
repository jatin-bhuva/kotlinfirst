package com.example.kotliin1

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class Lesson6S2 :  BaseActivity() {

    private lateinit var folderPicker: MaterialAutoCompleteTextView
    private lateinit var folderAdapter: ArrayAdapter<String>
    private lateinit var folderList: List<String>
    private lateinit var colorList: List<String>
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var statusBarColorPicker: MaterialAutoCompleteTextView
    private lateinit var actionBarColorPicker: MaterialAutoCompleteTextView
    private lateinit var colorAdapter: ArrayAdapter<String>

    private lateinit var actionBar: androidx.appcompat.widget.Toolbar

    private var savedFolderPath: String = ""
    private var savedStatusBarColor: String = ""
    private var savedActionBarColor: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lesson6_s2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeVariables()
        folderMenuListener()
        actionBarColorMenuListener()
        statusBarColorMenuListener()
    }

    private fun initializeVariables() {
        folderPicker = findViewById(R.id.folder_autocomplete)
        statusBarColorPicker = findViewById(R.id.status_bar_color_auto_complete)
        actionBarColorPicker = findViewById(R.id.action_bar_color_auto_complete)
        actionBar = findViewById(R.id.toolbar)

        setSupportActionBar(actionBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        folderList = listOf(getString(R.string.image_path), getString(R.string.image_path1))
        colorList = listOf(getString(R.string.Blue), getString(R.string.Green), getString(R.string.Red), getString(R.string.White))

        folderAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, folderList)
        colorAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, colorList)

        folderPicker.setAdapter(folderAdapter)
        statusBarColorPicker.setAdapter(colorAdapter)
        actionBarColorPicker.setAdapter(colorAdapter)

        sharedPreferences = getSharedPreferences(Constants.SETTING_PREFS_NAME, MODE_PRIVATE)
        savedFolderPath = sharedPreferences.getString(Constants.KEY_SELECTED_FOLDER_PATH, "").orEmpty()

        savedStatusBarColor = getColorFromPreferences(Constants.KEY_STATUS_BAR_COLOR, getString(R.string.Blue))
        savedActionBarColor = getColorFromPreferences(Constants.KEY_ACTION_BAR_COLOR, getString(R.string.Blue))

        setTextIfNotBlank(folderPicker, savedFolderPath)
        setTextIfNotBlank(statusBarColorPicker, savedStatusBarColor)
        setTextIfNotBlank(actionBarColorPicker, savedActionBarColor)
    }

    private fun setTextIfNotBlank(view: MaterialAutoCompleteTextView, text: String) {
        if (text.isNotBlank()) {
            view.setText(text, false)
        }
    }

    private fun folderMenuListener() {
        folderPicker.setOnItemClickListener { parent, _, position, _ ->
            val selectedFolder = parent.getItemAtPosition(position).toString()
            sharedPreferences.edit().putString(Constants.KEY_SELECTED_FOLDER_PATH, selectedFolder).apply()
        }
    }

    private fun actionBarColorMenuListener() {
        actionBarColorPicker.setOnItemClickListener { parent, _, position, _ ->
            val selectedColor = parent.getItemAtPosition(position).toString()
            applyColorToActionBar(selectedColor)
            sharedPreferences.edit().putString(Constants.KEY_ACTION_BAR_COLOR, selectedColor).apply()
        }
    }

    private fun statusBarColorMenuListener() {
        statusBarColorPicker.setOnItemClickListener { parent, _, position, _ ->
            val selectedColor = parent.getItemAtPosition(position).toString()
            applyColorToStatusBar(selectedColor)
            sharedPreferences.edit().putString(Constants.KEY_STATUS_BAR_COLOR, selectedColor).apply()
        }
    }

    private fun applyColorToStatusBar(color: String) {
        val colorInt = returnColorInt(color)
        window.statusBarColor = colorInt
    }

    private fun applyColorToActionBar(color: String) {
        val colorInt = returnColorInt(color)
        val colorDrawable = android.graphics.drawable.ColorDrawable(colorInt)
        supportActionBar?.setBackgroundDrawable(colorDrawable)
    }

    private fun returnColorInt(color: String): Int {
        val colorInt = when (color) {
            Constants.BLUE -> Color.BLUE
            Constants.GREEN -> Color.GREEN
            Constants.RED -> Color.RED
            else -> Color.WHITE
        }
        return colorInt
    }
}