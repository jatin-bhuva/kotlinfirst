package com.example.kotliin1.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _uiState = MutableLiveData(LoginUiState())
    val uiState: LiveData<LoginUiState> = _uiState

    fun handleIntent(intent: LoginAction){
        when (intent){
            is LoginAction.EmailChanged -> {
                _uiState.value = _uiState.value?.copy(
                    email = intent.email,
                    errorMessage = ""
                )
            }
            is LoginAction.PasswordChanged -> {
                _uiState.value = _uiState.value?.copy(
                    passWord = intent.password,
                    errorMessage = ""
                )

            }
            LoginAction.SubmitLogin -> {
                handleLogin()
            }
        }
    }

    private fun handleLogin(){
        val current   = _uiState.value?: return
        if(current.email.isBlank()||current.passWord.isBlank()){
            _uiState.value  = _uiState.value?.copy(
                errorMessage = "Email and Password can't be empty!"
            )
            return
        }
        _uiState.value = _uiState.value?.copy(
            errorMessage = "",
            isLoading = true
        )

        viewModelScope.launch {
            delay(3500)
            if(current.email=="test@gmail.com" && current.passWord=="Test@123"){
                _uiState.value = _uiState.value?.copy(
                    loginSuccess = true,
                    isLoading = false
                )
            }
            else{
                _uiState.value  = _uiState.value?.copy(
                    errorMessage = "Email or Password is incorrect!",
                    isLoading = false
                )
            }
        }
    }
}