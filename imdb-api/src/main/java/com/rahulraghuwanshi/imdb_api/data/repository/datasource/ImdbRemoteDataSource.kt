package com.rahulraghuwanshi.imdb_api.data.repository.datasource

import com.rahulraghuwanshi.imdb_api.model.MovieDetail
import com.rahulraghuwanshi.imdb_api.model.MovieResponse
import com.rahulraghuwanshi.imdb_api.util.RestClientResult

interface ImdbRemoteDataSource {

    suspend fun getMovieList(movieName: String): RestClientResult<MovieResponse?>
    suspend fun getMovieDetail(imdbId: String): RestClientResult<MovieDetail?>

}