package com.example.moviesnowplaying.data.services

import com.example.moviesnowplaying.BuildConfig


/**
 * @see  https://developers.themoviedb.org/3/getting-started/authentication
 */
object AuthType {
    const val BEARER = "Authorization: BEARER ${BuildConfig.TMDB_API_KEY}"
}

object ContentType {
    const val JSON = "Content-Type: application/json;charset=utf-8"
}