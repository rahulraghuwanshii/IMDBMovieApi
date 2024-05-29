package com.rahulraghuwanshi.movieimdb.movie_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulraghuwanshi.imdb_api.domain.use_case.GetMovieListUseCase
import com.rahulraghuwanshi.imdb_api.model.MovieResponse
import com.rahulraghuwanshi.imdb_api.util.RestClientResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    private val _movieListFlow = MutableSharedFlow<RestClientResult<MovieResponse?>>()
    val movieListFlow = _movieListFlow

    fun fetchMovieList(movieName: String) {
        viewModelScope.launch {
            Log.d("MAJAMA", "fetchMovieList: ")
            getMovieListUseCase.invoke(movieName).collectLatest {
                _movieListFlow.emit(it)
                Log.d("MAJAMA", "fetchMovieList() called $it")
            }
        }
    }
}