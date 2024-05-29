package com.rahulraghuwanshi.movieimdb.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulraghuwanshi.imdb_api.constants.ApiConstants
import com.rahulraghuwanshi.imdb_api.domain.use_case.GetMovieListUseCase
import com.rahulraghuwanshi.imdb_api.model.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    private val _movieListFlow = MutableSharedFlow<List<Search?>?>()
    val movieListFlow = _movieListFlow

    fun fetchMovieList(movieName: String) {
        viewModelScope.launch {
            getMovieListUseCase.invoke(movieName).collectLatest {
                _movieListFlow.emit(it.data?.Search)
            }
        }
    }
}