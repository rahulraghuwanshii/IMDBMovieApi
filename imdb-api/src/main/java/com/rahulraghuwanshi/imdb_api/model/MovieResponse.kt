package com.rahulraghuwanshi.imdb_api.model

data class MovieResponse(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)