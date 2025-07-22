package com.example.kotliin1

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.flexbox.FlexboxLayout
import kotlin.math.ceil
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {


 lateinit var input: EditText
 lateinit var flexBox: FlexboxLayout
 lateinit var superGridView: SquareGridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBottom1 :Button = findViewById(R.id.btn2);
        btnBottom1.setOnClickListener {
            val intent = Intent(this, Lession1_S3::class.java)
            startActivity(intent)
        }

        initializeViews()

        input.addTextChangedListener {
            val count = it.toString().toIntOrNull() ?: 0
            superGridView.setSquareCount(count)
        }

//        input.addTextChangedListener {
//            val count = it.toString().toIntOrNull() ?: 0
//            updateSquares(count)
//        }

    }
    fun initializeViews(){
        superGridView = findViewById(R.id.squareGridView)

//        flexBox = findViewById<FlexboxLayout>(R.id.squareContainer)
//        flexBox.post {
//            val width = flexBox.width
//            val params = flexBox.layoutParams
//            params.height = width  // Make it square
//            flexBox.layoutParams = params
//        }
        input = findViewById<EditText>(R.id.input1)
    }
    fun updateSquares(count: Int) {
        flexBox.removeAllViews()

        flexBox.post {
            val containerSize = flexBox.width  // since height == width
            if (count <= 0 || containerSize == 0) return@post

            val columns = ceil(sqrt(count.toDouble())).toInt()
            val squareSize = containerSize / columns

            for (i in 0 until count) {

                val square = View(this).apply {
                    layoutParams = FlexboxLayout.LayoutParams(squareSize, squareSize)

                    // Set border using GradientDrawable
                    background = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        setColor(Color.WHITE) // Inner color (or transparent)
                        setStroke(3, Color.BLACK) //  Border thickness & color
                    }
                }
                flexBox.addView(square)

            }
        }
    }

}