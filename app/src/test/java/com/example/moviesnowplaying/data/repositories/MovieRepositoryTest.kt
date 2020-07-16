package com.example.moviesnowplaying.data.repositories

import com.example.moviesnowplaying.data.services.MoviesService
import com.example.moviesnowplaying.network.Resource
import com.example.moviesnowplaying.network.response.ConfigurationResponse
import com.example.moviesnowplaying.network.response.NowPlayingResponse
import com.example.moviesnowplaying.utils.JsonUtil
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
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

    @Test
    fun `should return error when api error from getMoviesNowPlaying`() = runBlocking {

        // Given
        coEvery { moviesService.getNowPlaying(any()) } returns Response.error(
            401,
            ResponseBody.create(null, "Invalid api key".toByteArray())
        )

        // When
        val result = movieRepository.getMoviesNowPlaying()

        // Then
        assertThat(result).isInstanceOf(Resource.Error::class.java)

    }

    @Test
    fun `should return posterBaseurl`() = runBlocking {

        // Given
        val size = "w185"
        val configurationResponse =
            JsonUtil.jsonFileToObject<ConfigurationResponse>("/configuration.json")

        coEvery { moviesService.getConfiguration() } returns Response.success(configurationResponse)

        // When
        val result = movieRepository.getPosterBaseUrl(size)

        // Then
        assertThat(result).isInstanceOf(Resource.Success::class.java)

        val posterBaseUrl =
            configurationResponse.images?.baseUrl + configurationResponse.images?.posterSizes?.firstOrNull { it == size }
        assertThat((result as Resource.Success).data).isEqualTo(posterBaseUrl)
    }
}


/**
 * conclusion: we do not need KoinTest for unit tests, but we will need
 * for instrumented tests
 */
class MovieRepositoryKoinTest : KoinTest {

    @MockK
    private lateinit var moviesService: MoviesService

    private val movieRepository: MovieRepository by inject()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockk()  // we have to use Mockk or Mockito
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        startKoin {
            modules(
                // repositoryModule
                module {
                    factory<MovieRepository> { MovieRepositoryImpl(moviesService) }
                }
            )
        }
    }

    @After
    fun tearDown() {
        // need to stop koin
        stopKoin()
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