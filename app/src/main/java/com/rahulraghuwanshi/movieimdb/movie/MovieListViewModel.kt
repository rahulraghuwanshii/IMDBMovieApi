package com.rahulraghuwanshi.movieimdb.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulraghuwanshi.imdb_api.constants.ApiConstants
import com.rahulraghuwanshi.imdb_api.domain.use_case.GetMovieListUseCase
import com.rahulraghuwanshi.imdb_api.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    private val _movieListFlow = MutableSharedFlow<List<Movie?>?>()
    val movieListFlow = _movieListFlow

    fun fetchMovieList(movieName: String) {
        viewModelScope.launch {
            getMovieListUseCase.invoke(key = ApiConstants.API_KEY, movieName).collectLatest {
                _movieListFlow.emit(it)
            }
        }
    }
}