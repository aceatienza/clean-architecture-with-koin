package com.example.moviesnowplaying.di

import com.example.moviesnowplaying.data.repositories.MovieRepository
import com.example.moviesnowplaying.data.repositories.MovieRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    factory<MovieRepository> { MovieRepositoryImpl(get()) }
}