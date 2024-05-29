package com.rahulraghuwanshi.imdb_api.domain.use_case.impl

import com.rahulraghuwanshi.imdb_api.domain.repository.MovieRepository
import com.rahulraghuwanshi.imdb_api.domain.use_case.GetMovieUseCase

internal class GetMovieUseCaseImpl(
    private val movieRepository: MovieRepository
) : GetMovieUseCase {
    override suspend fun invoke(id: String) = movieRepository.getMovieDetail(id)
}