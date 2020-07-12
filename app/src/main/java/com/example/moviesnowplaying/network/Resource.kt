package com.example.moviesnowplaying.network

/**
 * Network Resource
 */
sealed class Resource<out T> {
    object  Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val error: Throwable) : Resource<Nothing>()
    object NotFound : Resource<Nothing>()
}