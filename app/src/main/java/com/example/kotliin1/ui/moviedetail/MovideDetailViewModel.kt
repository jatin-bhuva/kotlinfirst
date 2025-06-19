package com.example.kotliin1.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotliin1.domain.model.Cast
import com.example.kotliin1.domain.usecase.GetMovieCastUseCase
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val getCast: GetMovieCastUseCase) : ViewModel() {
    private val _cast = MutableLiveData<List<Cast>>()
    val cast: LiveData<List<Cast>> = _cast

    fun fetchCast(movieId: Int) {
        viewModelScope.launch {
            _cast.value = getCast(movieId).toList()
        }
    }
}
