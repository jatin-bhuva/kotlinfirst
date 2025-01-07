package com.example.kotliin1

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class Lesson3S2 : AppCompatActivity() {
    private lateinit var dateOfLaunch: Button
    private lateinit var bookName: EditText
    private lateinit var authorName: EditText
    private lateinit var genre: Spinner
    private lateinit var fictionGroup: RadioGroup
    private lateinit var kidsAge: CheckBox
    private lateinit var teensAge: CheckBox
    private lateinit var adultsAge: CheckBox
    private lateinit var calendar: Calendar
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private lateinit var selectedDateText: TextView
    private lateinit var submitBookBtn: Button

    private val genres =
        arrayOf("Select Genre", "Fantasy", "Science Fiction", "Romance", "Mystery", "Biography")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lesson3_s2)
        initializeVariables()
        window.statusBarColor = resources.getColor(android.R.color.white, theme)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bookName1 = intent.getStringExtra("BOOK_NAME")
        val authorName1 = intent.getStringExtra("AUTHOR_NAME")
        val genreVal = intent.getStringExtra("GENRE")
        val fictionType = intent.getStringExtra("FICTION_TYPE")
        val launchDate = intent.getStringExtra("LAUNCH_DATE")
        val ageGroup = intent.getStringArrayListExtra("AGE_GROUPS")
        val bookId = intent.getStringExtra("BOOK_ID")
        if (bookId != null) {
            bookName.setText(bookName1)
            authorName.setText(authorName1)
            selectedDateText.text = launchDate
            if (fictionType == "Fiction") {
                fictionGroup.check(R.id.fiction_radio)
            } else if (fictionType == "Non-Fiction") {
                fictionGroup.check(R.id.non_fiction_radio)
            }
            ageGroup?.let {
                kidsAge.isChecked = it.contains("Kids")
                teensAge.isChecked = it.contains("Teens")
                adultsAge.isChecked = it.contains("Adults")
            }

        }
        val genreSpinner: Spinner = findViewById(R.id.genre_spinner)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genreSpinner.adapter = adapter
        val genreIndex = genres.indexOf(genreVal)
        genre.setSelection(genreIndex)
        dateOfLaunch.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    val date = dateFormat.format(calendar.time)
                    selectedDateText.text = date
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        submitBookBtn.setOnClickListener {
            val selectedFictionType = when (fictionGroup.checkedRadioButtonId) {
                R.id.fiction_radio -> "Fiction"
                R.id.non_fiction_radio -> "Non-Fiction"
                else -> "Not Selected"
            }

            val selectedAgeGroups = mutableListOf<String>()
            if (kidsAge.isChecked) selectedAgeGroups.add("Kids")
            if (teensAge.isChecked) selectedAgeGroups.add("Teens")
            if (adultsAge.isChecked) selectedAgeGroups.add("Adults")

            val selectedGenre = genreSpinner.selectedItem.toString()
            val selectedDate = selectedDateText.text.toString()
            val book = Lesson3S1.Book(
                bookName = bookName.text.toString().trim(),
                authorName = authorName.text.toString().trim(),
                genre = selectedGenre,
                fictionType = selectedFictionType,
                launchDate = selectedDate,
                ageGroup = selectedAgeGroups,
                id = bookId ?: UUID.randomUUID().toString()
            )
            saveBookToSharedPreferences(book, bookId)
            Toast.makeText(this, "Book Added Successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

            else -> Log.d("x is neither 1 nor 2", item.itemId.toString())
        }
        return true
    }

    private fun initializeVariables() {
        bookName = findViewById(R.id.book_name)
        authorName = findViewById(R.id.author_name)
        genre = findViewById(R.id.genre_spinner)
        fictionGroup = findViewById(R.id.fiction_radio_group)
        dateOfLaunch = findViewById(R.id.date_picker_button)
        kidsAge = findViewById(R.id.age_group_kids)
        teensAge = findViewById(R.id.age_group_teens)
        adultsAge = findViewById(R.id.age_group_adults)
        calendar = Calendar.getInstance()
        selectedDateText = findViewById(R.id.selected_date)
        submitBookBtn = findViewById(R.id.submit_btn)
    }

    private fun saveBookToSharedPreferences(book: Lesson3S1.Book, bookId: String?) {
        val sharedPreferences = getSharedPreferences("BooksData", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val existingBooks = sharedPreferences.getString("bookList", "[]")
        val booksArray = JSONArray(existingBooks)
        var bookFound = false
        if (bookId != null) {
            if (bookId.isNotEmpty()) {
                for (i in 0 until booksArray.length()) {
                    val existingBook = booksArray.getJSONObject(i)
                    if (existingBook.getString("id") == bookId) {
                        existingBook.put("bookName", book.bookName)
                        existingBook.put("authorName", book.authorName)
                        existingBook.put("genre", book.genre)
                        existingBook.put("fictionType", book.fictionType)
                        existingBook.put("launchDate", book.launchDate)
                        existingBook.put("ageGroup", JSONArray(book.ageGroup))
                        bookFound = true
                        break
                    }
                }
            }
        }

        if (!bookFound) {
            booksArray.put(JSONObject().apply {
                put("bookName", book.bookName)
                put("authorName", book.authorName)
                put("genre", book.genre)
                put("fictionType", book.fictionType)
                put("launchDate", book.launchDate)
                put("ageGroup", JSONArray(book.ageGroup))
                put("id", book.id)
            })
        }
        editor.putString("bookList", booksArray.toString())
        editor.apply()
    }
}