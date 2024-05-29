package com.rahulraghuwanshi.imdb_api.domain

import com.rahulraghuwanshi.imdb_api.model.Movie
import retrofit2.Call

interface MovieRepository {


    fun groupMovieList(key: String, char: String): Call<List<Movie?>?>

    fun getMovieDetail(id: String): Movie

}