package com.galih.appfilm.core.di

import com.galih.appfilm.core.data.remote.MovieApi
import com.galih.appfilm.core.data.remote.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideMovieApi(): MovieApi = RetrofitInstance.api

}