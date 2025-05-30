package com.example.kotliin1.data.api

import com.example.kotliin1.data.model.Breed
import retrofit2.http.GET
import retrofit2.http.Headers

interface DogApiService {
    @Headers("x-api-key: live_PU7H3j4K22ox97vpYwTdAUdQ4XCfTJDgZkHGZMiK5JuA6FJ8zJdvifBBMj3BA8uM")
    @GET("v1/breeds")
    suspend fun getBreeds(): List<Breed>
}
