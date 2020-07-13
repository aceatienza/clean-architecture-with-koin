package com.example.moviesnowplaying.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesnowplaying.data.models.MovieShort
import com.example.moviesnowplaying.domain.MoviesNowPlayingUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val moviesNowPlayingUseCase: MoviesNowPlayingUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<List<MovieShort>?>()
    val movies: LiveData<List<MovieShort>?> get() = _movies

    fun getMovies() {
        viewModelScope.launch {
            moviesNowPlayingUseCase.execute(MoviesNowPlayingUseCase.Param).collect {
                val results = if (it is MoviesNowPlayingUseCase.Result.Success) it.movies else null
                _movies.postValue(results)
            }
        }
    }

}