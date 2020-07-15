package com.example.moviesnowplaying.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * https://developers.themoviedb.org/3/configuration/get-api-configuration
 *
 * note: use secure_base_url - https://developer.android.com/training/articles/security-config#CleartextTrafficPermitted
 */
@JsonClass(generateAdapter = true)
data class ConfigurationResponse(
    @Json(name = "images") val images: Images?
) {
    @JsonClass(generateAdapter = true)
    data class Images(
        @Json(name = "secure_base_url") val baseUrl: String?,
        @Json(name = "poster_sizes") val posterSizes: List<String>?
    )
}