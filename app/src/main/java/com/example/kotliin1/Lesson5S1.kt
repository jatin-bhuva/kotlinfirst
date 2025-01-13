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
            putExtra("LATITUDE", location.latitude)
            putExtra("LONGITUDE", location.longitude)
        }
        startActivity(intent)
    }

    private fun getBookMarksFromSharedPreferences(): List<Location> {
        val sharedPreferences = getSharedPreferences("BookMarkData", MODE_PRIVATE)
        val locationListString = sharedPreferences.getString("bookMarkList", "[]")

        val locationList = mutableListOf<Location>()

        try {
            val jsonArray = JSONArray(locationListString)
            for (i in 0 until jsonArray.length()) {
                val locationJson = jsonArray.getJSONObject(i)
                val latitude = locationJson.getDouble("latitude")
                val longitude = locationJson.getDouble("longitude")
                locationList.add(Location(latitude, longitude))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return locationList
    }

    private fun deleteLocation(location: Location) {
        val sharedPreferences = getSharedPreferences("BookMarkData", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val locationListString = sharedPreferences.getString("bookMarkList", "[]")
        val jsonArray = JSONArray(locationListString)

        val updatedJsonArray = JSONArray()

        for (i in 0 until jsonArray.length()) {
            val locationJson = jsonArray.getJSONObject(i)
            val latitude = locationJson.getDouble("latitude")
            val longitude = locationJson.getDouble("longitude")
            if (latitude != location.latitude || longitude != location.longitude) {
                updatedJsonArray.put(locationJson)
            }
        }
        editor.putString("bookMarkList", updatedJsonArray.toString())
        editor.apply()
        setupRecyclerView()
    }

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null) return

            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)

            batteryPercent.text = "${level.toInt()} %"
        }
    }

}