package com.example.kotliin1

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SolarSystem : AppCompatActivity() {


lateinit var recyclerView: RecyclerView
private val planetList = mutableListOf<ListData>()
    private var idCounter = 1
val adapter  = ListAdapterCustom()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_solar_system)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeViews()
        animatePlanets()


    }

    fun initializeViews(){
        recyclerView=findViewById<RecyclerView>(R.id.listAdapterDemo)

        recyclerView.adapter= adapter
        startAutoUpdatingList()
        }

    fun animatePlanets() {

    }
    private fun startAutoUpdatingList() {
        lifecycleScope.launch {
            while (idCounter<50) {
                delay(1500)
                val newItem = ListData(idCounter, 'P', "Planet $idCounter")
                planetList.add(newItem)
                adapter.submitList(planetList.toList()) {
                }
                idCounter++
            }
        }
    }
}