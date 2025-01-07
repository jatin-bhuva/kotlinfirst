package com.example.kotliin1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class Lesson3S3 : AppCompatActivity() {
    private lateinit var bookNameView: TextView
    private lateinit var authorNameView: TextView
    private lateinit var genreView: TextView
    private lateinit var fictionTypeView: TextView
    private lateinit var launchDateView: TextView
    private lateinit var ageGroupView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lesson3_s3)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        window.statusBarColor = resources.getColor(android.R.color.white, theme)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bookName = intent.getStringExtra("BOOK_NAME")
        val authorName = intent.getStringExtra("AUTHOR_NAME")
        val genre = intent.getStringExtra("GENRE")
        val fictionType = intent.getStringExtra("FICTION_TYPE")
        val launchDate = intent.getStringExtra("LAUNCH_DATE")
        val ageGroup = intent.getStringArrayListExtra("AGE_GROUPS")

        initializeVariables()
        bookNameView.text = bookName
        authorNameView.text = authorName
        genreView.text = genre
        fictionTypeView.text = fictionType
        launchDateView.text = launchDate
        ageGroupView.text = ageGroup.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("tes", "sssss${item}")
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.edit -> {
                val editIntent = Intent(this, Lesson3S2::class.java)
                editIntent.putExtras(intent.extras ?: Bundle())
                startActivity(editIntent)
            }
            else -> Log.d("x is neither 1 nor 2", item.itemId.toString())
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return true;
    }

    private fun initializeVariables() {
        bookNameView = findViewById(R.id.book_name)
        authorNameView = findViewById(R.id.author_name)
        genreView = findViewById(R.id.genre)
        fictionTypeView = findViewById(R.id.fiction_type)
        launchDateView = findViewById(R.id.date_of_launch)
        ageGroupView = findViewById(R.id.age_group)
    }
}