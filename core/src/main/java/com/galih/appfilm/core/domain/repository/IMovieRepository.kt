package com.galih.appfilm.core.domain.repository

import com.galih.appfilm.core.domain.model.Movie
import com.galih.appfilm.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<Resource<List<Movie>>>

    suspend fun searchMovie(query: String): Flow<Resource<List<Movie>>>

    suspend fun upsert(movie: Movie): Long

    fun getAllFavMovies(): Flow<List<Movie>>

    suspend fun deleteFavMovie(movie: Movie)
}