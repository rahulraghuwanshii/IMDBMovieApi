package com.rahulraghuwanshi.imdb_api.data.network

import com.rahulraghuwanshi.imdb_api.model.MovieDetail
import com.rahulraghuwanshi.imdb_api.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiRequest {

    companion object {
        const val BASE_URL = "https://www.omdbapi.com/"
        const val API_KEY =  "587b8fce"
    }

    @GET("/")
    suspend fun getMovies(
        @Query("s") search: String,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ) : Response<MovieResponse>


    @GET("/")
    suspend fun getMovieDetail(
        @Query("i") imdbId: String,
        @Query("apiKey") apiKey: String = API_KEY
    ) : Response<MovieDetail>
}