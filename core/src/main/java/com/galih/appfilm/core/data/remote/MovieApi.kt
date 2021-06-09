package com.galih.appfilm.core.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import com.galih.appfilm.core.BuildConfig.API_KEY

interface MovieApi {
    @GET("trending/movie/day")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = API_KEY
    ) : MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String
    ) : MovieResponse
}