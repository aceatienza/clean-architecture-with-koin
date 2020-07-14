package com.example.moviesnowplaying.data.repositories

import com.example.moviesnowplaying.data.services.MoviesService
import com.example.moviesnowplaying.network.Resource
import com.example.moviesnowplaying.network.response.NowPlayingResponse
import com.example.moviesnowplaying.utils.JsonUtil
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class MovieRepositoryTest {

    @MockK
    private lateinit var moviesService: MoviesService

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        movieRepository = MovieRepositoryImpl(moviesService)
    }

    @Test
    fun `should return movie shorts`() = runBlocking {

        // Given
        val nowPlayingResponse = JsonUtil.jsonFileToObject<NowPlayingResponse>("/now-playing.json")

        coEvery { moviesService.getNowPlaying(any()) } returns Response.success(nowPlayingResponse)

        // When
        val result = movieRepository.getMoviesNowPlaying()

        // Then
        assertThat(result).isInstanceOf(Resource.Success::class.java)

        val firstMovieShort = nowPlayingResponse.results!!.first().toModel()
        assertThat((result as Resource.Success).data.first()).isEqualTo(firstMovieShort)
    }

}