package com.rahulraghuwanshi.imdb_api.di

import com.rahulraghuwanshi.imdb_api.constants.ApiConstants.BASE_URL
import com.rahulraghuwanshi.imdb_api.data.MovieDataSource
import com.rahulraghuwanshi.imdb_api.data.MovieRepositoryImpl
import com.rahulraghuwanshi.imdb_api.domain.repository.MovieRepository
import com.rahulraghuwanshi.imdb_api.domain.use_case.GetMovieListUseCase
import com.rahulraghuwanshi.imdb_api.domain.use_case.GetMovieUseCase
import com.rahulraghuwanshi.imdb_api.domain.use_case.impl.GetMovieListUseCaseImpl
import com.rahulraghuwanshi.imdb_api.domain.use_case.impl.GetMovieUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ImdbModule {

    @Provides
    @Singleton
    fun provideGetMovieUseCase(movieRepository: MovieRepository): GetMovieUseCase {
        return GetMovieUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun provideGetMovieListUseCase(movieRepository: MovieRepository): GetMovieListUseCase {
        return GetMovieListUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieDataSource: MovieDataSource): MovieRepository {
        return MovieRepositoryImpl(movieDataSource)
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): MovieDataSource =
        retrofit.create(MovieDataSource::class.java)


}