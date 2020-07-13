package com.example.moviesnowplaying.data.services

import com.example.moviesnowplaying.BuildConfig
import com.example.moviesnowplaying.TMDB_QUERY_PARAM_API_KEY
import com.example.moviesnowplaying.network.response.NowPlayingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface MoviesService {

    @Headers(
        ContentType.JSON
    )
    @GET("movie/now_playing")
    suspend fun getNowPlaying(@Query(TMDB_QUERY_PARAM_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY): Response<NowPlayingResponse>
}