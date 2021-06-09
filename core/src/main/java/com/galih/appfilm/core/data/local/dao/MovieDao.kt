package com.galih.appfilm.core.data.local.dao

import androidx.room.*
import com.galih.appfilm.core.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovie()

    
}