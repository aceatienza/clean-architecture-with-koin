package com.example.moviesnowplaying.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object JsonUtil {

    /**
     * usage ex: "/movies.json" from /resources
     */
    inline fun <reified T> jsonFileToObject(path: String): T {

        val jsonString: String = try {
            val res = javaClass.getResource(path)
            res?.readText(Charsets.UTF_8)
        } catch (e: Exception) {
            throw JsonUtilException(e.message)
        }
            ?: throw JsonUtilException("null json")

        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)

        return adapter.fromJson(jsonString) ?: throw JsonUtilException()
    }

    class JsonUtilException(private val msg: String? = "") : Exception(msg)
}