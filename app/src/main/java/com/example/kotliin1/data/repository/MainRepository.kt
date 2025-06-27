package com.example.kotliin1.data.repository

import com.example.kotliin1.data.model.Recipe

interface MainRepository {
    suspend fun doGetRecipeApiCall(id :String) :  Recipe
}