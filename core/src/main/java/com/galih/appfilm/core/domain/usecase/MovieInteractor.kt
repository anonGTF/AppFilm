package com.galih.appfilm.core.domain.usecase

import com.galih.appfilm.core.domain.model.Movie
import com.galih.appfilm.core.domain.repository.IMovieRepository
import com.galih.appfilm.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: IMovieRepository
) : MovieUseCase {
    override fun getMovies(): Flow<Resource<List<Movie>>> = movieRepository.getMovies()

    override suspend fun searchMovie(query: String): Flow<Resource<List<Movie>>> =
        movieRepository.searchMovie(query)

    override suspend fun upsert(movie: Movie): Long = movieRepository.upsert(movie)

    override fun getAllFavMovies(): Flow<List<Movie>> = movieRepository.getAllFavMovies()

    override suspend fun deleteFavMovie(movie: Movie) = movieRepository.deleteFavMovie(movie)
}