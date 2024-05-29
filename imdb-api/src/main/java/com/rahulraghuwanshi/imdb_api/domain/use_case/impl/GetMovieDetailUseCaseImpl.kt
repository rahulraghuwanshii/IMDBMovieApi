package com.rahulraghuwanshi.imdb_api.domain.use_case.impl

import com.rahulraghuwanshi.imdb_api.domain.repository.MovieRepository
import com.rahulraghuwanshi.imdb_api.domain.use_case.GetMovieDetailUseCase

internal class GetMovieDetailUseCaseImpl(
    private val movieRepository: MovieRepository
) : GetMovieDetailUseCase {
    override suspend fun invoke(imdbId: String) = movieRepository.getMovieDetail(imdbId)
}