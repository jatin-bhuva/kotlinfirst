package com.example.kotliin1.data.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotliin1.data.api.DogApiService
import com.example.kotliin1.data.model.Breed

class DogRepository(private val api: DogApiService) {
    private val breedsLiveData = MutableLiveData<List<Breed>>()

    private val breedDataLoading = MutableLiveData<Boolean>()

    val breedsList: LiveData<List<Breed>>
        get() = breedsLiveData
    val dataLoading: LiveData<Boolean>
        get() = breedDataLoading
    suspend fun getBreeds() {
        breedDataLoading.postValue(true)
        val result = api.getBreeds()
        if (result.isNotEmpty()){
            breedsLiveData.postValue(result)
        }
        breedDataLoading.postValue(false)

    }
}
