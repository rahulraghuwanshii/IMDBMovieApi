package com.rahulraghuwanshi.imdb_api.data.repository.datasourceImpl

import com.rahulraghuwanshi.imdb_api.data.network.MovieApiInterface
import com.rahulraghuwanshi.imdb_api.data.repository.datasource.ImdbRemoteDataSource
import com.rahulraghuwanshi.imdb_api.util.RestClientResult
import retrofit2.Response

class ImdbRemoteDataSourceImpl(
    private val movieApiInterface: MovieApiInterface
) : ImdbRemoteDataSource {

    private suspend fun <T> getResult(
        apiCall: suspend () -> Response<T>,
    ): RestClientResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                    RestClientResult.success(body)
                else
                    RestClientResult.error(response.message())
            } else
                RestClientResult.error(response.message())
        } catch (e: Exception) {
            RestClientResult.error(e.message ?: "Something went wrong")
        }
    }

    override suspend fun getMovieList(movieName: String) = getResult {
        movieApiInterface.getMoviesList(movieName)
    }

    override suspend fun getMovieDetail(imdbId: String) = getResult {
        movieApiInterface.getMovieDetail(imdbId)
    }
}