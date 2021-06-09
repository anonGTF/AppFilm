package com.galih.appfilm.core.data

import androidx.room.withTransaction
import com.galih.appfilm.core.data.local.MovieDatabase
import com.galih.appfilm.core.data.remote.MovieApi
import com.galih.appfilm.core.domain.model.Movie
import com.galih.appfilm.core.domain.repository.IMovieRepository
import com.galih.appfilm.core.utils.DataMapper
import com.galih.appfilm.core.utils.Resource
import com.galih.appfilm.core.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val api: MovieApi,
    private val db: MovieDatabase
) : IMovieRepository {

    private val movieDao = db.getMovieDao()
    private val favMovieDao = db.getFavMovieDao()

    override fun getMovies() = networkBoundResource(
        query = {
            movieDao.getAllMovies().map {
                DataMapper.mapEntitiesToDomain(it)
            }
        },
        fetch = {
            api.getMovies().results
        },
        saveFetchResult = { movies ->
            db.withTransaction {
                movieDao.deleteAllMovie()
                movieDao.insertMovies(DataMapper.mapDomainToEntities(movies))
            }
        }
    )

    override suspend fun searchMovie(query: String): Flow<Resource<List<Movie>>> =
        flow {
            emit(Resource.Loading())
            val res = api.searchMovies(query = query).results
            emit(Resource.Success(res))
        }

    override suspend fun upsert(movie: Movie): Long =
            favMovieDao.upsert(DataMapper.mapDomainToFavEntity(movie))

    override fun getAllFavMovies(): Flow<List<Movie>> = favMovieDao.getAllMovies().map {
        DataMapper.mapFavEntitiesToDomain(it)
    }

    override suspend fun deleteFavMovie(movie: Movie) =
            favMovieDao.deleteMovie(DataMapper.mapDomainToFavEntity(movie))
}