package com.coreen.popularmovies.model

import android.os.Parcel
import android.os.Parcelable

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
data class Movie constructor(val title: String?, private val relativeImagePath: String?,
                             val summary: String?, val releaseDate: String?,
                             val voteAvg: String?) : Parcelable {

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


    /**
     * TODO(coyuen): can be simplified with Kotlin v1.1.4, on v1.2.30
     *
     * https://proandroiddev.com/parcelable-in-kotlin-here-comes-parcelize-b998d5a5fcac
     */
    constructor(parcel: Parcel?) : this(parcel!!.readString(), parcel.readString(),
            parcel.readString(), parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        // only need non-null check for first, all subsequent calls assume based off first call
        dest!!.writeString(title)
        dest.writeString(relativeImagePath)
        dest.writeString(summary)
        dest.writeString(releaseDate)
        dest.writeString(voteAvg)
    }

    override fun describeContents(): Int {
        return this.hashCode()
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls<Movie?>(size)
        }

        override fun createFromParcel(source: Parcel?): Movie {
            return Movie(source)
        }

    }
}