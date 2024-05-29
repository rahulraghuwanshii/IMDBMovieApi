package com.priyanshub.swiggyapp.data.model

data class MovieResponse(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)