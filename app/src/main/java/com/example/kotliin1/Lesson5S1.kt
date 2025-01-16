package com.example.kotliin1

import BookmarkAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotliin1.util.Constant
import org.json.JSONArray

class Lesson5S1 : AppCompatActivity() {

    private lateinit var addButton: Button
    private lateinit var locationList: RecyclerView
    private lateinit var locations: List<Location>
    private lateinit var bookMarkAdapter: BookmarkAdapter
    private lateinit var batteryPercent: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lesson5_s1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeVariables()
        val intent = Intent(this, MyService::class.java)
        startService(intent)

        setupRecyclerView()
        addButton.setOnClickListener {
            navigateToActivity(Lesson5Map::class.java)
        }
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    private fun initializeVariables() {
        addButton = findViewById(R.id.add_location)
        locationList = findViewById(R.id.recyclerViewBookmarks)
        batteryPercent = findViewById(R.id.battery_indi)
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    override fun onRestart() {
        locations = getBookMarksFromSharedPreferences()
        setupRecyclerView()
        super.onRestart()
    }

    private fun setupRecyclerView() {
        locations = getBookMarksFromSharedPreferences()
        bookMarkAdapter = BookmarkAdapter(
            locations,
            onItemClicked = { location -> navigateToLocationDetails(location) },
            onDeleteClick = { location -> deleteLocation(location) }
        )
        locationList.adapter = bookMarkAdapter
        locationList.layoutManager = LinearLayoutManager(this)
    }

    private fun navigateToLocationDetails(location: Location) {
        val intent = Intent(this, Lesson5S2::class.java).apply {
            putExtra(Constant.CAP_LATITUDE, location.latitude)
            putExtra(Constant.CAP_LONGITUDE, location.longitude)
        }
        startActivity(intent)
    }

    private fun getBookMarksFromSharedPreferences(): List<Location> {
        val sharedPreferences = getSharedPreferences(Constant.BOOK_MARK_PREF, MODE_PRIVATE)
        val locationListString = sharedPreferences.getString(Constant.BOOK_MARK_LIST, "[]")

        val locationList = mutableListOf<Location>()

        try {
            val jsonArray = JSONArray(locationListString)
            for (i in 0 until jsonArray.length()) {
                val locationJson = jsonArray.getJSONObject(i)
                val latitude = locationJson.getDouble(Constant.LATITUDE)
                val longitude = locationJson.getDouble(Constant.LONGITUDE)
                locationList.add(Location(latitude, longitude))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return locationList
    }

    private fun deleteLocation(location: Location) {
        val sharedPreferences = getSharedPreferences(Constant.BOOK_MARK_PREF, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val locationListString = sharedPreferences.getString(Constant.BOOK_MARK_LIST, "[]")
        val jsonArray = JSONArray(locationListString)

        val updatedJsonArray = JSONArray()

        for (i in 0 until jsonArray.length()) {
            val locationJson = jsonArray.getJSONObject(i)
            val latitude = locationJson.getDouble(Constant.LATITUDE)
            val longitude = locationJson.getDouble(Constant.LONGITUDE)
            if (latitude != location.latitude || longitude != location.longitude) {
                updatedJsonArray.put(locationJson)
            }
        }
        editor.putString(Constant.BOOK_MARK_LIST, updatedJsonArray.toString())
        editor.apply()
        setupRecyclerView()
    }

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null) return

            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)

            "${level.toInt()} %".also { batteryPercent.text = it }
        }
    }

}