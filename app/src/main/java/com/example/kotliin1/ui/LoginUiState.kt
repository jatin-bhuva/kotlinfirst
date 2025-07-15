package com.example.kotliin1.ui

data class LoginUiState(
    val email: String = "",
    val passWord: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val loginSuccess: Boolean = false
)
