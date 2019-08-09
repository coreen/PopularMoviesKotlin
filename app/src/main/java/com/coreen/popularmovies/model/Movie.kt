package com.coreen.popularmovies.model

/**
 * Constants defined outside class to maintain equivalent as "private static final"
 *
 * https://blog.egorand.me/where-do-i-put-my-constants-in-kotlin/
 */
const val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/"
const val IMAGE_DEFAULT_POSTER_SIZE = "w185"

/**
 * Primary constructor is part of class header
 *
 * https://kotlinlang.org/docs/reference/data-classes.html
 */
data class Movie constructor(val title: String, private val relativeImagePath: String,
                             val summary: String, val releaseDate: String, val voteAvg: String) {
    /**
     * Getters and setters automatically defined (val => read-only, no setters; var => both)
     *
     * https://android.jlelse.eu/kotlin-for-android-developers-data-class-c2ad51a32844?gi=fe9ab7de7cce
     */

    /**
     * Custom getter overrides
     *
     * https://kotlinlang.org/docs/reference/properties.html
     */
    val imagePath: String
        get() = IMAGE_BASE_URL + IMAGE_DEFAULT_POSTER_SIZE + relativeImagePath
}