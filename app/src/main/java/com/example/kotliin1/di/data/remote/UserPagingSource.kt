package com.example.kotliin1.di.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kotliin1.di.data.model.User
import com.example.kotliin1.di.data.repository.UserRepository

class UserPagingSource(
    private val userRepository: UserRepository
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentPage = params.key ?: 5
            val response = userRepository.fetchUses(limit = 5, skip = 5*currentPage)
            LoadResult.Page(
                data = response.users,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage*5 == response.total) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int?  {
        return state.anchorPosition?.let { position->
            val page = state.closestPageToPosition((position))
            page?.prevKey?.minus(1)?:page?.nextKey?.plus(1)
        }
    }
}
