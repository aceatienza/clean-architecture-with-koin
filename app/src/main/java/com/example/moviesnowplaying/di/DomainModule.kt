package com.example.moviesnowplaying.di

import com.example.moviesnowplaying.domain.MoviesNowPlayingUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule: Module = module {
    factory { MoviesNowPlayingUseCase(get()) }
}