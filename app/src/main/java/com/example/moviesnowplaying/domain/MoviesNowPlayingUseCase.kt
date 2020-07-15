package com.example.moviesnowplaying.domain

import com.example.moviesnowplaying.data.models.MovieShort
import com.example.moviesnowplaying.data.repositories.MovieRepository
import com.example.moviesnowplaying.network.Resource
import kotlinx.coroutines.flow.*

class MoviesNowPlayingUseCase(
    private val movieRepository: MovieRepository
) : FlowUseCase<MoviesNowPlayingUseCase.Param, MoviesNowPlayingUseCase.Result> {

    override fun execute(param: Param): Flow<Result> = flow {

        emit(Result.Loading)

        val getMoviesNowPlayingFlow = flowOf(movieRepository.getMoviesNowPlaying())
        val getPosterBaseUrlFlow = flowOf(movieRepository.getPosterBaseUrl())

        /**
         *  handle multiple async api calls
         */
        combine(
            getMoviesNowPlayingFlow,
            getPosterBaseUrlFlow
        ) { moviesNowResource, baseUrlResource ->

            if (moviesNowResource is Resource.Success && moviesNowResource.data.isNotEmpty() && baseUrlResource is Resource.Success) {
                val movies = moviesNowResource.data.map {
                    it.copy(posterPath = baseUrlResource.data + it.posterPath)
                }

                return@combine Result.Success(movies)
            }

            if (moviesNowResource is Resource.Success && moviesNowResource.data.isNotEmpty() && baseUrlResource is Resource.Success) {
                return@combine Result.Error(Throwable("There was an error retrieving move list"))
            }

            Result.Loading
        }.collect {
            emit(it)
        }

    }

    object Param

    sealed class Result {
        object Loading : Result()
        data class Success(
            val movies: List<MovieShort>
        ) : Result()

        data class Error(val error: Throwable) : Result()
    }

}