package com.example.moviesnowplaying.domain

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<Param, Result> {
    fun execute(param: Param): Flow<Result>
}