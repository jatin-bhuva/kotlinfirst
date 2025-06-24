package com.example.kotliin1.di.data.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kotliin1.di.data.model.User
import com.example.kotliin1.di.data.model.UserResponse
import com.example.kotliin1.di.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository ) : ViewModel(){

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val pagedUsers = repository.getPagedUsers()
        .cachedIn(viewModelScope)

    init {
        fetchUser()
    }

    private fun fetchUser(){
        viewModelScope.launch {
            try{
                _users.value = repository.fetchUses(limit = 5, skip = 5).users
            }
            catch (e:Exception){
                Log.d("UserViewModel",e.toString())
            }
        }
    }
}