package com.example.moviesnowplaying.data.repositories

import com.example.moviesnowplaying.data.models.MovieShort
import com.example.moviesnowplaying.data.services.MoviesService
import com.example.moviesnowplaying.network.Resource

interface MovieRepository {
    suspend fun getMoviesNowPlaying(): Resource<List<MovieShort>>

    // note: default poster size - we could dynamically choose the closest if needed
    suspend fun getPosterBaseUrl(size: String = "w185"): Resource<String>
}

class MovieRepositoryImpl(
    private val moviesService: MoviesService
) : MovieRepository {

    // note: we could separate out into own ConfigurationRepository and ConfigurationService
    override suspend fun getPosterBaseUrl(size: String): Resource<String> {
        return try {
            val response = moviesService.getConfiguration()

            if (response.isSuccessful) {
                val configurationResponse = response.body()

                // TODO: handle null cases
                val posterBaseUrl =
                    configurationResponse?.images?.baseUrl + configurationResponse?.images?.posterSizes?.firstOrNull { it == size }

                if (!posterBaseUrl.isNullOrEmpty())
                    Resource.Success(posterBaseUrl)
                else
                    Resource.NotFound

            } else {
                Resource.Error(Throwable(response.errorBody()?.string()))
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

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