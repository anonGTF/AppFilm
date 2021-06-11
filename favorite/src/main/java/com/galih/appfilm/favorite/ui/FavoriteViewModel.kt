package com.galih.appfilm.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.galih.appfilm.core.domain.model.Movie
import com.galih.appfilm.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class FavoriteViewModel (
    private val movieUseCase: MovieUseCase
    ): ViewModel() {

    fun getData(): LiveData<List<Movie>> = movieUseCase.getAllFavMovies().asLiveData()

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        movieUseCase.deleteFavMovie(movie)
    }

    fun saveMovie(movie: Movie) = viewModelScope.launch {
        movieUseCase.upsert(movie)
    }
}