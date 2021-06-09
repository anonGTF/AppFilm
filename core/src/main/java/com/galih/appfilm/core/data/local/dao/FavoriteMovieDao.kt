package com.galih.appfilm.core.data.local.dao

import androidx.room.*
import com.galih.appfilm.core.data.local.entities.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: FavoriteMovieEntity): Long

    @Query("SELECT * FROM fav_movies")
    fun getAllMovies(): Flow<List<FavoriteMovieEntity>>

    @Delete
    suspend fun deleteMovie(movie: FavoriteMovieEntity)
}