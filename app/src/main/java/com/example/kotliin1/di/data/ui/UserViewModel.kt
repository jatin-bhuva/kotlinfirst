package com.example.kotliin1.di.data.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.kotliin1.di.data.remote.UserPagingSource
import com.example.kotliin1.di.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository ) : ViewModel(){

    private val userPager = Pager(PagingConfig(pageSize = 5)) {
        UserPagingSource(repository)
    }.flow
    val pagedUsers = userPager.cachedIn(viewModelScope)

}