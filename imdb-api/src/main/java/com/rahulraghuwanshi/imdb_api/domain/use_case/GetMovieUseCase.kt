package com.rahulraghuwanshi.imdb_api.domain.use_case

import com.rahulraghuwanshi.imdb_api.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetMovieUseCase {

    suspend operator fun invoke(id: String): Flow<Movie>
}