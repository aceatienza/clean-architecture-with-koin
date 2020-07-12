package com.example.moviesnowplaying.domain

import com.example.moviesnowplaying.data.models.MovieShort
import com.example.moviesnowplaying.data.repositories.MovieRepository
import com.example.moviesnowplaying.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesNowPlayingUseCase(
    private val movieRepository: MovieRepository
) : FlowUseCase<MoviesNowPlayingUseCase.Param, MoviesNowPlayingUseCase.Result> {

    override fun execute(param: Param): Flow<Result> = flow {

        emit(Result.Loading)

        when(val resource = movieRepository.getMoviesNowPlaying()) {
            Resource.Loading -> TODO()
            is Resource.Success -> Result.Success(resource.data)
            is Resource.Error -> TODO()
            Resource.NotFound -> TODO()
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