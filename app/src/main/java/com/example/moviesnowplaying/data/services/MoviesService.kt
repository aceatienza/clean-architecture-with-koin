package com.example.moviesnowplaying.data.services

import com.example.moviesnowplaying.network.response.NowPlayingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MoviesService {

    @Headers(
        AuthType.BEARER,
        ContentType.JSON
    )
    @GET("/movie/now_playing")
    suspend fun getNowPlaying(): Response<NowPlayingResponse>
}