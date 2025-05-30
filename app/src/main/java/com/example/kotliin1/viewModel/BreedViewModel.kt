package com.example.kotliin1.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotliin1.data.model.Breed
import com.example.kotliin1.data.repository.DogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BreedViewModel(private val repository: DogRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBreeds()
        }
    }
    val breeds: LiveData<List<Breed>>
        get() = repository.breedsList

    val breedsLoading: LiveData<Boolean>
        get() = repository.dataLoading
}
