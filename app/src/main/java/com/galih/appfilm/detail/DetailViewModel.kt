package com.galih.appfilm.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galih.appfilm.core.domain.model.Movie
import com.galih.appfilm.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    val item: MutableLiveData<Movie> = MutableLiveData()

    fun setItem(movie: Movie) {
        item.postValue(movie)
    }

    fun saveMovie(movie: Movie) = viewModelScope.launch {
        movieUseCase.upsert(movie)
    }
}