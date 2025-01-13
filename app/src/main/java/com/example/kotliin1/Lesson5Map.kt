package com.example.kotliin1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import org.json.JSONObject


data class Location(val latitude: Double, val longitude: Double)

class Lesson5Map : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var btnConfirmLocation: Button

    private var selectedLatLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lesson5_map)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeVariables()

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        btnConfirmLocation.setOnClickListener {
            saveLocationToList()
        }
    }

    private fun initializeVariables() {
        mapView = findViewById(R.id.mapView)
        btnConfirmLocation = findViewById(R.id.btnConfirmLocation)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setOnMapClickListener { latLng ->
            googleMap.clear()
            googleMap.addMarker(
                MarkerOptions().position(latLng).title(getString(R.string.add_location))
            )
            selectedLatLng = latLng
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    private fun saveLocationToList() {
        val sharedPreferences = getSharedPreferences("BookMarkData", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val existingData = sharedPreferences.getString("bookMarkList", "[]")
        val locationArray = JSONArray(existingData)
        selectedLatLng?.let {
            locationArray.put(JSONObject().apply {
                put("latitude", it.latitude)
                put("longitude", it.longitude)
            })
        }
        editor.putString("bookMarkList", locationArray.toString())
        editor.apply()
        finish()
    }
}