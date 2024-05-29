package com.rahulraghuwanshi.imdb_api.di

import com.rahulraghuwanshi.imdb_api.constants.ApiConstants.BASE_URL
import com.rahulraghuwanshi.imdb_api.data.network.MovieApiInterface
import com.rahulraghuwanshi.imdb_api.data.repository.MovieRepositoryImpl
import com.rahulraghuwanshi.imdb_api.data.repository.datasource.ImdbRemoteDataSource
import com.rahulraghuwanshi.imdb_api.data.repository.datasourceImpl.ImdbRemoteDataSourceImpl
import com.rahulraghuwanshi.imdb_api.domain.repository.MovieRepository
import com.rahulraghuwanshi.imdb_api.domain.use_case.GetMovieListUseCase
import com.rahulraghuwanshi.imdb_api.domain.use_case.GetMovieDetailUseCase
import com.rahulraghuwanshi.imdb_api.domain.use_case.impl.GetMovieListUseCaseImpl
import com.rahulraghuwanshi.imdb_api.domain.use_case.impl.GetMovieDetailUseCaseImpl
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
    fun provideGetMovieUseCase(movieRepository: MovieRepository): GetMovieDetailUseCase {
        return GetMovieDetailUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun provideGetMovieListUseCase(movieRepository: MovieRepository): GetMovieListUseCase {
        return GetMovieListUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(imdbRemoteDataSource: ImdbRemoteDataSource): MovieRepository {
        return MovieRepositoryImpl(imdbRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideImdbRemoteDataSource(movieApiInterface: MovieApiInterface): ImdbRemoteDataSource {
        return ImdbRemoteDataSourceImpl(movieApiInterface)
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
    fun provideService(retrofit: Retrofit): MovieApiInterface =
        retrofit.create(MovieApiInterface::class.java)


}