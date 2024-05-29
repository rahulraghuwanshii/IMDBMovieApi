package com.rahulraghuwanshi.imdb_api.domain.use_case

import com.rahulraghuwanshi.imdb_api.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetMovieListUseCase {

    suspend operator fun invoke(key: String, char: String): Flow<List<Movie?>?>
}