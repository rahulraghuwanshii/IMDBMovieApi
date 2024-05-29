package com.rahulraghuwanshi.imdb_api.data

import com.rahulraghuwanshi.imdb_api.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDataSource {

    @GET
    fun groupMovieList(@Query("key") key: String, @Query("s") char: String): List<Movie?>?

    @GET
    fun getMovieDetail(@Query("i") id: String): Movie

}