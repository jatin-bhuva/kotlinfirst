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

class Lesson3S2 : AppCompatActivity() {
    lateinit var dateOfLaunch: Button;
    lateinit var bookName: EditText;
    lateinit var authorName: EditText;
    lateinit var genre: Spinner;
    lateinit var fictionGroup: RadioGroup;
    lateinit var kidsAge: CheckBox;
    lateinit var teensAge: CheckBox;
    lateinit var adultsAge: CheckBox;
    lateinit var calendar: Calendar
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    lateinit var selectedDateText: TextView
    lateinit var submitBookBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lesson3_s2)
        initializeVariables()
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val genreSpinner: Spinner = findViewById(R.id.genre_spinner)
        val genres =
            arrayOf("Select Genre", "Fantasy", "Science Fiction", "Romance", "Mystery", "Biography")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genreSpinner.adapter = adapter

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
                ageGroup = selectedAgeGroups
            )
            saveBookToSharedPreferences(book)
            Log.d(
                "AddBook", """
                Book Name: ${bookName.text.toString().trim()}
                Author Name: ${authorName.text.toString().trim()}
                Genre: $selectedGenre
                Fiction Type: $selectedFictionType
                Date of Launch: $selectedDate
                Age Groups: ${selectedAgeGroups.joinToString(", ")}
            """.trimIndent()
            )
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
        bookName = findViewById(R.id.book_name);
        authorName = findViewById(R.id.author_name);
        genre = findViewById(R.id.genre_spinner);
        fictionGroup = findViewById(R.id.fiction_radio_group);
        dateOfLaunch = findViewById(R.id.date_picker_button);
        kidsAge = findViewById(R.id.age_group_kids)
        teensAge = findViewById(R.id.age_group_teens)
        adultsAge = findViewById(R.id.age_group_adults)
        calendar = Calendar.getInstance()
        selectedDateText = findViewById(R.id.selected_date)
        submitBookBtn = findViewById(R.id.submit_btn)
    }

    fun saveBookToSharedPreferences(book: Lesson3S1.Book) {
        val sharedPreferences = getSharedPreferences("BooksData", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val existingBooks = sharedPreferences.getString("bookList", "[]")
        val booksArray = JSONArray(existingBooks)

        val newBook = JSONObject().apply {
            put("bookName", book.bookName)
            put("authorName", book.authorName)
            put("genre", book.genre)
            put("fictionType", book.fictionType)
            put("launchDate", book.launchDate)
            put("ageGroup", JSONArray(book.ageGroup))
        }

        booksArray.put(newBook)
        editor.putString("bookList", booksArray.toString())
        editor.apply()
    }
}