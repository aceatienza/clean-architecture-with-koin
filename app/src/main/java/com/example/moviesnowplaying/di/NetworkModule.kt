package com.example.moviesnowplaying.di

import com.example.moviesnowplaying.TMDB_BASE_URL
import com.example.moviesnowplaying.data.services.MoviesService
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

val networkModule: Module = module {

    single { provideMoshi() }

    /**
     * OkHttp performs best when you create a single OkHttpClient instance
     * and reuse it for all of your HTTP calls
     */
    single { provideOkHttpClient() }

    single { provideRetrofit(get()) }

    factory { provideMoviesService(get()) }
}

private fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

private fun provideMoshi(): Moshi {
    return Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .add(KotlinJsonAdapterFactory())
        .build()
}

private fun provideRetrofit(moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

private fun provideMoviesService(retrofit: Retrofit): MoviesService {
    return  retrofit.create(MoviesService::class.java)
}