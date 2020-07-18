package com.example.moviesnowplaying.ui.main

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import com.example.moviesnowplaying.BaseAndroidTest
import com.example.moviesnowplaying.EspressoUtil.nthChildOf
import com.example.moviesnowplaying.R
import com.example.moviesnowplaying.network.response.NowPlayingResponse
import com.example.moviesnowplaying.utils.JsonUtil
import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test

@LargeTest
class MainFragmentTest : BaseAndroidTest() {

    private val nowPlayingPath = "/now-playing.json"
    private val json = JsonUtil.getStringFromResource(nowPlayingPath)!!
    private val adapter = Moshi.Builder().build().adapter(NowPlayingResponse::class.java)
    private val nowPlayingResponse = adapter.fromJson(json)!!

    @Before
    override fun setup() {
        super.setup()

        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.requestUrl?.encodedPath) {
                    "/configuration" -> {
                        val json = JsonUtil.getStringFromResource("/configuration.json")!!
                        return MockResponse().setResponseCode(200).setBody(json)
                    }
                    "/movie/now_playing" -> {
                        val json = JsonUtil.getStringFromResource(nowPlayingPath)!!
                        return MockResponse().setResponseCode(200).setBody(json)
                    }
                    else -> MockResponse().setResponseCode(404)
                }
            }

        }
    }

    @Test
    @Throws(Throwable::class)
    fun should_see_first_movie() {

        // Given
        val position = 0
        val movieShort = nowPlayingResponse.results!!.first().toModel()

        // When
        launchActivity()

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.rv_movies_short))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(nthChildOf(ViewMatchers.withId(R.id.rv_movies_short), position))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.hasDescendant(
                        Matchers.allOf(
                            ViewMatchers.withId(
                                R.id.txt_title
                            ), ViewMatchers.withText(movieShort.title)
                        )
                    )
                )
            )

        Espresso.onView(nthChildOf(ViewMatchers.withId(R.id.rv_movies_short), position))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.hasDescendant(
                        Matchers.allOf(
                            ViewMatchers.withId(
                                R.id.txt_overview
                            ), ViewMatchers.withText(movieShort.overview)
                        )
                    )
                )
            )

    }

    @Test
    @Throws(Throwable::class)
    fun should_see_second_movie() {

        // Given
        val position = 1
        val movieShort = nowPlayingResponse.results!![position].toModel()

        // When
        launchActivity()

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.rv_movies_short))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(nthChildOf(ViewMatchers.withId(R.id.rv_movies_short), position))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.hasDescendant(
                        Matchers.allOf(
                            ViewMatchers.withId(
                                R.id.txt_title
                            ), ViewMatchers.withText(movieShort.title)
                        )
                    )
                )
            )

        Espresso.onView(nthChildOf(ViewMatchers.withId(R.id.rv_movies_short), position))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.hasDescendant(
                        Matchers.allOf(
                            ViewMatchers.withId(
                                R.id.txt_overview
                            ), ViewMatchers.withText(movieShort.overview)
                        )
                    )
                )
            )

    }
}