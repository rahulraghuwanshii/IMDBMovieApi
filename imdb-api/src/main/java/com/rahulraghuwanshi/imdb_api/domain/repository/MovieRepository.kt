package com.rahulraghuwanshi.imdb_api.domain.repository

import com.rahulraghuwanshi.imdb_api.model.MovieDetail
import com.rahulraghuwanshi.imdb_api.model.MovieResponse
import com.rahulraghuwanshi.imdb_api.util.RestClientResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {


    suspend fun groupMovieList(movieName: String): Flow<RestClientResult<MovieResponse?>>

    suspend fun getMovieDetail(imdbId: String): Flow<RestClientResult<MovieDetail?>>

}