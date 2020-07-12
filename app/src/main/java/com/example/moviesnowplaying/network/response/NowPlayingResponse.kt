package com.example.moviesnowplaying.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * https://developers.themoviedb.org/3/movies/get-now-playing
 */
@JsonClass(generateAdapter = true)
data class NowPlayingResponse (
    @Json(name = "dates") val dates: Dates?,
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<MovieListResult>?,
    @Json(name = "total_pages") val total_pages: Int?,
    @Json(name = "total_results") val total_results: Int?
    ) {

    /**
     * @TODO - use DTO to transform into local model
     */
    @JsonClass(generateAdapter = true)
    data class MovieListResult(
        @Json(name = "id") val id: Int?,
        @Json(name = "overview") val overview: String?,
        @Json(name = "poster_path") val posterPath: String?,
        @Json(name = "release_date") val releaseDate: String?,
        @Json(name = "title") val title: String?
    )

    @JsonClass(generateAdapter = true)
    data class Dates (
        @Json(name = "maximum") val maximum: String?,
        @Json(name = "maximum") val minimum: String?
    )
}