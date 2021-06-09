package com.galih.appfilm.core.di

import com.galih.appfilm.core.data.MovieRepository
import com.galih.appfilm.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepository: MovieRepository): IMovieRepository
}