package com.example.kotliin1.di.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kotliin1.di.data.model.User
import com.example.kotliin1.di.data.model.UserResponse
import com.example.kotliin1.di.data.remote.ApiService
import com.example.kotliin1.di.data.remote.UserPagingSource
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun fetchUses(limit: Int, skip: Int): UserResponse {
        return apiService.getUsers(limit, skip)
    }

    override fun getPagedUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { UserPagingSource(apiService) }
        ).flow
    }
}