package com.coreen.popularmovies.utility

import android.net.Uri
import timber.log.Timber
import java.net.URL

/**
 * Static methods are declared without the class wrapper in Kotlin.
 *
 * https://stackoverflow.com/questions/40352684/what-is-the-equivalent-of-java-static-methods-in-kotlin
 */

const val MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie"
const val POPULAR_MOVIES_PATH = "popular"
const val TOP_RATED_MOVIES_PATH = "top_rated"
const val API_KEY_QUERY = "api_key"

enum class PathType {
    POPULAR_MOVIES, TOP_RATED_MOVIES
}

fun buildPopularMoviesUrl(): URL = NetworkUtil().buildUrl(PathType.POPULAR_MOVIES)

fun buildTopRatedMoviesUrl(): URL = NetworkUtil().buildUrl(PathType.TOP_RATED_MOVIES)

class NetworkUtil {
    fun buildUrl(pathType: PathType): URL {
        var builtUri = Uri.parse(MOVIE_DB_BASE_URL)
                .buildUpon()
                .appendPath(
                        // no ternary operator in Kotlin :/
                        if (pathType == PathType.TOP_RATED_MOVIES)
                            TOP_RATED_MOVIES_PATH
                        else
                        // use popular movies by default
                        POPULAR_MOVIES_PATH
                )
                .appendQueryParameter(API_KEY_QUERY, com.coreen.popularmovies.BuildConfig.apikey)
        try {
            val url = URL(builtUri.toString())
            Timber.v("Built url: " + url.toString())
            return url
        } catch (exception: Exception) {
            exception.printStackTrace()
            return URL("") // equivalent of null, but Kotlin is NPE-sensitive so this is used instead
        }
    }
}