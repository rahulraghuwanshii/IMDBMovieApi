package com.rahulraghuwanshi.imdb_api.data.network

import com.rahulraghuwanshi.imdb_api.constants.ApiConstants
import com.rahulraghuwanshi.imdb_api.model.MovieDetail
import com.rahulraghuwanshi.imdb_api.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiInterface {


    @GET("/")
    suspend fun getMoviesList(
        @Query("s") search: String,
        @Query("apiKey") apiKey: String = ApiConstants.API_KEY,
    ) : Response<MovieResponse>


    @GET("/")
    suspend fun getMovieDetail(
        @Query("i") imdbId: String,
        @Query("apiKey") apiKey: String = ApiConstants.API_KEY
    ) : Response<MovieDetail>
}