package com.example.kotliin1.data.repository

import com.example.kotliin1.data.model.Recipe
import com.example.kotliin1.data.remote.MyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepositoryImpl(
    private val api:MyApi
): MainRepository {
    override suspend fun doGetRecipeApiCall(id: String): Recipe = withContext(Dispatchers.IO) {
        api.getRecipe(id)
    }

}