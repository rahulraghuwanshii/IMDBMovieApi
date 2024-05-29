package com.rahulraghuwanshi.movieimdb.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulraghuwanshi.imdb_api.domain.use_case.GetMovieDetailUseCase
import com.rahulraghuwanshi.imdb_api.model.MovieDetail
import com.rahulraghuwanshi.imdb_api.util.RestClientResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _movieDetailFlow = MutableSharedFlow<RestClientResult<MovieDetail?>>()
    val movieDetailFlow = _movieDetailFlow

    fun fetchMovieDetails(imdbId: String) {
        viewModelScope.launch {
            getMovieDetailUseCase.invoke(imdbId).collectLatest {
                _movieDetailFlow.emit(it)
            }
        }
    }
}