package com.example.kotliin1.data.api

import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitMovieHelper {
    private const val BASE_URL= "https://api.themoviedb.org/3/"
    private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1MTIxMzVlMWIyYzI5M2E1N2Y2MmE0MmYzMDY4NGFiMSIsIm5iZiI6MTc0ODkyOTczMS42NDgsInN1YiI6IjY4M2U4Y2MzM2FjOTA4ZDZiNWZkYmI2ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.st7z6UaTtWCZK3K3VA9ZImD0xiHhnow8r8E-lIhQ23M"

    fun getInstance(): Retrofit {

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $BEARER_TOKEN")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}


