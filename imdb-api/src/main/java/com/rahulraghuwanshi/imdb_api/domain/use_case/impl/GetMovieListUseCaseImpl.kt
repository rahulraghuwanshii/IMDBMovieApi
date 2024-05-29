package com.rahulraghuwanshi.imdb_api.domain.use_case.impl

import com.rahulraghuwanshi.imdb_api.domain.repository.MovieRepository
import com.rahulraghuwanshi.imdb_api.domain.use_case.GetMovieListUseCase

internal class GetMovieListUseCaseImpl(
    private val movieRepository: MovieRepository
) : GetMovieListUseCase {
    override suspend fun invoke(key: String, char: String) = movieRepository.groupMovieList(key, char)
}