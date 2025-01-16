package com.example.kotliin1

import WeatherConnect
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.kotliin1.util.Constant
import com.example.kotliin1.util.WeatherResponse
import com.google.gson.Gson
import kotlin.properties.Delegates

class Lesson5S2 : AppCompatActivity(), WeatherConnect.WeatherCallback {
    private var latitude by Delegates.notNull<Double>()
    private var longitude by Delegates.notNull<Double>()
    private lateinit var weatherData: WeatherResponse
    private lateinit var cityName: TextView
    private lateinit var temperatureTxt: TextView
    private lateinit var humidityTxt: TextView
    private lateinit var windSpeedTxt: TextView
    private lateinit var weatherTxt: TextView
    private lateinit var weatherImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lesson5_s2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeVariables()
        latitude = intent.getDoubleExtra(Constant.LATITUDE, 0.0)
        longitude = intent.getDoubleExtra(Constant.LONGITUDE, 0.0)
        val url =
            "${Constant.WEATHER_API_END_POINT_MAIN}?lat=${latitude}&lon=${longitude}&appid=${BuildConfig.WEATHER_API_KEY}"

        WeatherConnect(this).execute(url)
    }

    override fun onWeatherResult(result: String) {
        val gson = Gson()
        weatherData = gson.fromJson(result, WeatherResponse::class.java)
        assignValuesToFields()
    }

    override fun onWeatherError(errorMessage: String) {
        Log.e("weather error:::::", errorMessage)
    }
    private fun initializeVariables(){
        cityName = findViewById(R.id.city)
        temperatureTxt = findViewById(R.id.temp)
        humidityTxt  = findViewById(R.id.humidity)
        windSpeedTxt = findViewById(R.id.wind_speed)
        weatherTxt = findViewById(R.id.weatherTxt)
        weatherImage = findViewById(R.id.weatherIcon)
    }

    private fun assignValuesToFields(){
        val celsius = weatherData.main.temp - 273.15
        cityName.text = getString(R.string.city_label, weatherData.name)
        temperatureTxt.text = getString(R.string.temperature_label, celsius)
        humidityTxt.text = getString(R.string.humidity_label, weatherData.main.humidity)
        windSpeedTxt.text = getString(R.string.wind_label, weatherData.wind.speed)

        val iconCode = weatherData.weather[0].icon

        val iconUrl = "${Constant.WEATHER_API_END_POINT}$iconCode.png"

        Glide.with(this)
            .load(iconUrl)
            .into(weatherImage)

        weatherTxt.text = weatherData.weather[0].main
    }


}