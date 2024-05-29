package com.rahulraghuwanshi.imdb_api.domain.use_case

import com.rahulraghuwanshi.imdb_api.model.MovieResponse
import com.rahulraghuwanshi.imdb_api.util.RestClientResult
import kotlinx.coroutines.flow.Flow

interface GetMovieListUseCase {

    suspend operator fun invoke(movieName: String): Flow<RestClientResult<MovieResponse?>>
}