package com.rahulraghuwanshi.imdb_api.data.repository

import com.rahulraghuwanshi.imdb_api.data.repository.datasource.ImdbRemoteDataSource
import com.rahulraghuwanshi.imdb_api.domain.repository.MovieRepository
import com.rahulraghuwanshi.imdb_api.model.MovieResponse
import com.rahulraghuwanshi.imdb_api.util.RestClientResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class MovieRepositoryImpl(
    private val imdbRemoteDataSource: ImdbRemoteDataSource
) : MovieRepository {

    private suspend fun <T> getFlowResult(call: suspend () -> RestClientResult<T>): Flow<RestClientResult<T>> =
        flow {
            emit(RestClientResult.loading())
            try {
                val result = call.invoke()
                emit(result)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(RestClientResult.error("Something went wrong with ${e.message}"))
            }
        }


    override suspend fun groupMovieList(movieName: String) = getFlowResult {
        imdbRemoteDataSource.getMovieList(movieName)
    }

    override suspend fun getMovieDetail(imdbId: String) = getFlowResult {
        imdbRemoteDataSource.getMovieDetail(imdbId)
    }
}