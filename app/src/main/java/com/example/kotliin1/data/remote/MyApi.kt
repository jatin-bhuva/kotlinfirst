package com.example.kotliin1.data.remote

import com.example.kotliin1.data.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApi {
    @GET("/recipes/{id}")
    suspend fun getRecipe(@Path("id") id:String): Recipe
}