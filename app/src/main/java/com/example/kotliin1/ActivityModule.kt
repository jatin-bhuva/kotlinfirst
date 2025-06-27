package com.example.kotliin1

import com.example.kotliin1.data.remote.MyApi
import com.example.kotliin1.data.repository.MainRepository
import com.example.kotliin1.data.repository.MainRepositoryImpl
import com.example.kotliin1.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val activityModule = module {
    scope<Koin> {
        scoped(qualifier = named("first")) { "Hello" }

        scoped(qualifier = named("second")) { "Second Hello" }
    }

}