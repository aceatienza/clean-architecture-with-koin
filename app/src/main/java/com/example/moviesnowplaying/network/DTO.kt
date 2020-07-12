package com.example.moviesnowplaying.network

/**
 * Data Transfer Object from network json response to model
 */
interface DTO<M> {
    fun toModel(): M
}

fun <M> List<DTO<M>>.toModelList(): List<M> {
    val output = ArrayList<M>()

    for (elem in this) output.add(elem.toModel())

    return output
}