package com.example.moviesnowplaying.data.models


/**
 * @TODO: releaseDate should be OffsetDateTime
 */
data class MovieShort (
    val id: Int,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String
)