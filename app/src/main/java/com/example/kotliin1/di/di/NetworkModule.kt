package com.example.kotliin1.di.di

import com.example.kotliin1.di.data.remote.ApiService
import com.example.kotliin1.di.data.repository.UserRepository
import com.example.kotliin1.di.data.repository.UserRepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBaseUrl() = "https://dummyjson.com/"

    @Provides
    @Singleton
    fun provideApiService(BASE_URL: String): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService):UserRepository{
        return UserRepositoryImpl(apiService)
    }

}