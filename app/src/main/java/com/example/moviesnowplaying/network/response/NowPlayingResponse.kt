package com.example.moviesnowplaying.network.response

import com.example.moviesnowplaying.data.models.MovieShort
import com.example.moviesnowplaying.network.DTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * https://developers.themoviedb.org/3/movies/get-now-playing
 */
@JsonClass(generateAdapter = true)
data class NowPlayingResponse(
    //@Json(name = "dates") val dates: Dates?, // TODO: retrofit is unable to create a converter with this field, is it a keyword? error message: "Unable to create converter for class"
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<MovieListResult>?,
    @Json(name = "total_pages") val total_pages: Int?,
    @Json(name = "total_results") val total_results: Int?
) {

    @JsonClass(generateAdapter = true)
    data class MovieListResult(
        @Json(name = "id") val id: Int?,
        @Json(name = "overview") val overview: String?,
        @Json(name = "poster_path") val posterPath: String?,
        @Json(name = "release_date") val releaseDate: String?,
        @Json(name = "title") val title: String?
    ) : DTO<MovieShort> {
        override fun toModel(): MovieShort =
            MovieShort(
                id = id ?: throw NullPointerException("missing id"),
                overview = overview,
                posterPath = posterPath,
                releaseDate = releaseDate,
                title = title ?: throw NullPointerException("missing title")
            )
    }

    @JsonClass(generateAdapter = true)
    data class Dates(
        @Json(name = "maximum") val maximum: String?,
        @Json(name = "maximum") val minimum: String?
    )
}