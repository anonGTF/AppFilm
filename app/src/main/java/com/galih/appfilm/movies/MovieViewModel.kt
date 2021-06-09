package com.galih.appfilm.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.galih.appfilm.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    fun getData() = movieUseCase.getMovies().asLiveData()

}