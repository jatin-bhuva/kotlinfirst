package com.example.kotliin1.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotliin1.data.model.Recipe
import com.example.kotliin1.data.repository.MainRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Result<out T> {
    data object Loading : Result<Nothing>()
    data object Ideal : Result<Nothing>()

    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

@OptIn(kotlinx.coroutines.FlowPreview::class)
class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private  val intentChannel = Channel<RecipeIntent>(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Idle)
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()
    val recipe: StateFlow<Recipe?> = uiState
        .map { state -> if (state is RecipeUiState.Success) state.recipe else null }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
    init {
        processIntents()
    }

    fun sendIntent(intent: RecipeIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    // Collect intents and process them
    private fun processIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is RecipeIntent.LoadRecipe -> doNetWorkCall(intent.recipeId)
                }
            }
        }
    }




    private fun doNetWorkCall(recipeId: String) {
        Log.d("START", "strt.....")

        viewModelScope.launch {
            try {
                _uiState.value = RecipeUiState.Loading
                val recipe = repository.doGetRecipeApiCall(recipeId)
                Log.d("RESPONSE", "TESTTTTTTT" + recipe.toString())
                _uiState.value = RecipeUiState.Success(recipe)
            } catch (e: Exception) {
                Log.d("ERRRZOR", "Erorrr......" + e.toString())
                _uiState.value = RecipeUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

}