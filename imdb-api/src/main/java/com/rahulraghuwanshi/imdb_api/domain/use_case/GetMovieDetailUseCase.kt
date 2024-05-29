package com.rahulraghuwanshi.imdb_api.domain.use_case

import com.rahulraghuwanshi.imdb_api.model.MovieDetail
import com.rahulraghuwanshi.imdb_api.util.RestClientResult
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailUseCase {

    suspend operator fun invoke(imdbId: String): Flow<RestClientResult<MovieDetail?>>
}