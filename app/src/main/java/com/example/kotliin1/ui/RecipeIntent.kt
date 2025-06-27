package com.example.kotliin1.ui

sealed class RecipeIntent {
    data class LoadRecipe(val recipeId: String) : RecipeIntent()
}