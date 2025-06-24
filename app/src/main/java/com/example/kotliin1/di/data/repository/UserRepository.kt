package com.example.kotliin1.di.data.repository

import androidx.paging.PagingData
import com.example.kotliin1.di.data.model.User
import com.example.kotliin1.di.data.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun fetchUses(limit: Int, skip: Int): UserResponse
}