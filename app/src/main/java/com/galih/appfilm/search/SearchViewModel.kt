package com.galih.appfilm.search

import androidx.lifecycle.*
import com.galih.appfilm.core.domain.model.Movie
import com.galih.appfilm.core.domain.usecase.MovieUseCase
import com.galih.appfilm.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    var data: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    fun searchMovie(query: String) = viewModelScope.launch {
        movieUseCase.searchMovie(query).collect { value -> data.postValue(value) }
    }
}