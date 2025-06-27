package com.example.kotliin1.ui

import com.example.kotliin1.data.model.Recipe

sealed class RecipeUiState {
    data object Loading : RecipeUiState()
    data class Success(val recipe: Recipe) : RecipeUiState()
    data class Error(val message: String) : RecipeUiState()
    data object Idle : RecipeUiState()
}