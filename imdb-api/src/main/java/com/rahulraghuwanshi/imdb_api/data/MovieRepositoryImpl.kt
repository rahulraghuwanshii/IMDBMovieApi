package com.rahulraghuwanshi.imdb_api.data

import com.rahulraghuwanshi.imdb_api.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flow

internal class MovieRepositoryImpl(
    private val movieDataSource: MovieDataSource
) : MovieRepository {
    override suspend fun groupMovieList(key: String, char: String) = flow{
        emit(movieDataSource.groupMovieList(key, char))
    }

    override suspend fun getMovieDetail(id: String) = flow{ emit(movieDataSource.getMovieDetail(id)) }
}