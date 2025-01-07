package com.example.kotliin1

import BookAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.util.UUID

class Lesson3S1 : AppCompatActivity() {

    data class Book(
        val bookName: String,
        val authorName: String,
        val genre: String,
        val fictionType: String,
        val launchDate: String,
        val ageGroup: List<String>,
        val id: String
    )

    lateinit var booksList: List<Book>
    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var bookSearchView: androidx.appcompat.widget.SearchView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var sortBy: Spinner
    private lateinit var filterBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = resources.getColor(android.R.color.white, theme)
        setContentView(R.layout.activity_lesson3_s1)
        initializeVariables()
        booksList = getBooksFromSharedPreferences()
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // search functionality
        bookSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val data = getBooksFromSharedPreferences()
                bookAdapter.filter(newText ?: "", data);
                return true
            }
        })

        // sort functionality
        sortBy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                val sortedBooks = when (position) {
                    0 -> booksList.toMutableList().apply { sortBy { it.launchDate } }
                    1 -> booksList.toMutableList().apply { sortBy { it.bookName } }
                    else -> booksList
                }
                bookAdapter.filter("", sortedBooks)
                bookAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        // open filter dialog
        filterBtn.setOnClickListener {
            openFilterDialog(booksList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_book -> navigateToAddBook()
            R.id.action_add -> navigateToAddBook()
            else -> Log.d("x is neither 1 nor 2", item.itemId.toString())
        }
        return true
    }

    private fun navigateToAddBook() {
        val i = Intent(applicationContext, Lesson3S2::class.java)
        startActivity(i)
    }

    override fun onRestart() {
        getBooksFromSharedPreferences()
        super.onRestart()
    }

    private fun getBooksFromSharedPreferences(): List<Book> {
        val sharedPreferences = getSharedPreferences("BooksData", MODE_PRIVATE)
        val booksList1 = mutableListOf<Book>()
        val booksArray = JSONArray(sharedPreferences.getString("bookList", "[]"))

        for (i in 0 until booksArray.length()) {
            val bookObject = booksArray.getJSONObject(i)
            val book = Book(bookName = bookObject.getString("bookName"),
                authorName = bookObject.getString("authorName"),
                id = bookObject.getString("id"),
                genre = bookObject.getString("genre"),
                fictionType = bookObject.getString("fictionType"),
                launchDate = bookObject.getString("launchDate"),
                ageGroup = List(bookObject.getJSONArray("ageGroup").length()) { index ->
                    bookObject.getJSONArray("ageGroup").getString(index)
                })
            booksList1.add(book)
        }
        bookAdapter = BookAdapter(booksList1) { book ->
            onBookItemClick(book)
        }
        bookRecyclerView.layoutManager = LinearLayoutManager(this)
        bookRecyclerView.adapter = bookAdapter
        booksList = booksList1
        return booksList1
    }

    private fun initializeVariables() {
        bookRecyclerView = findViewById(R.id.bookRecyclerView)
        bookSearchView = findViewById((R.id.bookSearchView))
        sortBy = findViewById(R.id.sort_by);
        filterBtn = findViewById(R.id.filter)

        val genres = arrayOf("Date of Launch", "Book Name")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortBy.adapter = adapter
    }

    private fun openFilterDialog(booksList: List<Book>) {
        val dialogView = layoutInflater.inflate(R.layout.book_filter_dialog, null)

        val authorEditText = dialogView.findViewById<EditText>(R.id.authorEditText)
        val genreSpinner = dialogView.findViewById<Spinner>(R.id.genreSpinner)
        val fictionTypeSpinner = dialogView.findViewById<Spinner>(R.id.fictionTypeSpinner)

        val genres = arrayOf("All", "Mystery", "Science Fiction", "Fantasy", "Non-Fiction")
        val fictionTypes = arrayOf("All", "Fiction", "Non-Fiction")

        val genreAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
        val fictionTypeAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, fictionTypes)

        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fictionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        genreSpinner.adapter = genreAdapter
        fictionTypeSpinner.adapter = fictionTypeAdapter

        val builder = AlertDialog.Builder(this).setTitle("Filter Books").setView(dialogView)
            .setPositiveButton("Apply") { dialog, _ ->
                val author = authorEditText.text.toString()
                val genre = genreSpinner.selectedItem.toString()
                val fictionType = fictionTypeSpinner.selectedItem.toString()
                val filteredBooks = booksList.filter {
                    (author.isEmpty() || it.authorName.contains(
                        author,
                        ignoreCase = true
                    )) && (genre == "All" || it.genre == genre) && (fictionType == "All" || it.fictionType == fictionType)
                }
                bookAdapter.filter("", filteredBooks)
                dialog.dismiss()
            }.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }

    private fun onBookItemClick(book: Lesson3S1.Book) {
        val intent = Intent(this, Lesson3S3::class.java)
        intent.putExtra("BOOK_NAME", book.bookName)
        intent.putExtra("AUTHOR_NAME", book.authorName)
        intent.putExtra("GENRE", book.genre)
        intent.putExtra("FICTION_TYPE", book.fictionType)
        intent.putExtra("LAUNCH_DATE", book.launchDate)
        intent.putExtra("BOOK_ID", book.id)
        intent.putStringArrayListExtra("AGE_GROUPS", ArrayList(book.ageGroup))
        startActivity(intent)
    }
}