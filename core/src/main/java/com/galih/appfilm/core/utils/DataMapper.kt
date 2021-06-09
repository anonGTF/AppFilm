package com.galih.appfilm.core.utils

import com.galih.appfilm.core.data.local.entities.FavoriteMovieEntity
import com.galih.appfilm.core.data.local.entities.MovieEntity
import com.galih.appfilm.core.domain.model.Movie

object DataMapper {

    fun mapEntitiesToDomain(input: List<MovieEntity>) : List<Movie> =
        input.map {
            Movie(
                id =  it.id,
                title = it.title,
                release_date = it.release_date,
                overview = it.overview,
                poster_path = it.poster_path,
                backdrop_path = it.backdrop_path
            )
    }

    fun mapDomainToEntities(input: List<Movie>): List<MovieEntity> =
        input.map {
            MovieEntity(
                id =  it.id,
                title = it.title,
                release_date = it.release_date,
                overview = it.overview,
                poster_path = it.poster_path,
                backdrop_path = it.backdrop_path
            )
        }

    fun mapFavEntitiesToDomain(input: List<FavoriteMovieEntity>) : List<Movie> =
            input.map {
                Movie(
                        id =  it.id,
                        title = it.title,
                        release_date = it.release_date,
                        overview = it.overview,
                        poster_path = it.poster_path,
                        backdrop_path = it.backdrop_path
                )
            }

    fun mapDomainToFavEntity(input: Movie): FavoriteMovieEntity =
            FavoriteMovieEntity(
                        id =  input.id,
                        title = input.title,
                        release_date = input.release_date,
                        overview = input.overview,
                        poster_path = input.poster_path,
                        backdrop_path = input.backdrop_path
                )

}