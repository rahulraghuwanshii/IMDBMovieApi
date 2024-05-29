package com.rahulraghuwanshi.imdb_api.domain.repository

import com.rahulraghuwanshi.imdb_api.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {


    suspend fun groupMovieList(key: String, char: String): Flow<List<Movie?>?>

    suspend fun getMovieDetail(id: String): Flow<Movie>

}