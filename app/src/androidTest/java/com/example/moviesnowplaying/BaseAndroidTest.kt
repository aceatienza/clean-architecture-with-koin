package com.example.moviesnowplaying

import android.Manifest
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.example.moviesnowplaying.di.*
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class BaseAndroidTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE
    )

    lateinit var app: TestApp

    private val server = MockWebServer()

    @Before
    open fun setup() {
        app = ApplicationProvider.getApplicationContext()

        // TODO: add Dispatcher to MockWebServer and replace /configuration and /now_playing responses with local json

        server.start()
    }

    @After
    fun teardown() {
        activityRule.finishActivity()
    }

    fun launchActivity(
        modules: List<Module> = listOf(
            appModule,
            networkModule,
            repositoryModule,
            domainModule,
            viewModelModule,
            module {
                // override only retrofit - this is easier than having a Dagger multi-module setup and separated shared modules
                single(override = true) { provideRetrofit(get(), get()) }
            }
        )
    ) {
        app.inject(modules)
        activityRule.launchActivity(null)
    }


    private fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        //  url() needs to be off the main thread
        val url = runBlocking(Dispatchers.IO) {
            server.url("/")
        }

        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}