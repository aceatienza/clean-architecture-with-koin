package com.example.moviesnowplaying.data.repositories

import com.example.moviesnowplaying.data.models.MovieShort
import com.example.moviesnowplaying.data.services.MoviesService
import com.example.moviesnowplaying.network.Resource

interface MovieRepository {
    suspend fun getMoviesNowPlaying(): Resource<List<MovieShort>>
}

class MovieRepositoryImpl(
    private val moviesService: MoviesService
) : MovieRepository {
    override suspend fun getMoviesNowPlaying(): Resource<List<MovieShort>> {
        return try {
            val response = moviesService.getNowPlaying()

            if (response.isSuccessful) {
                val nowPlayingResponse = response.body()

                val movieShorts = nowPlayingResponse?.results?.mapNotNull {
                    try {
                        it.toModel()
                    } catch (e: NullPointerException) {
                        null // allow successfully processed models through
                    }
                }

                if (!movieShorts.isNullOrEmpty())
                    Resource.Success(movieShorts)
                else
                    Resource.NotFound

            } else {
                Resource.Error(Throwable(response.errorBody()?.string()))
            }
            
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}