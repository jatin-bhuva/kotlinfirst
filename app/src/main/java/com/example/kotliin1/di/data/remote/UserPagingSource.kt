package com.example.kotliin1.di.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kotliin1.di.data.model.User

class UserPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getUsers(limit = 5, skip = 5*currentPage)
            LoadResult.Page(
                data = response.users,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage*5 == response.total) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int = 1
}
