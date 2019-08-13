package com.coreen.popularmovies.utility

import com.coreen.popularmovies.model.Movie
import com.google.gson.Gson
import org.json.JSONObject

const val RESULTS = "results"

class MovieDbJsonUtil {
    fun parseApiJson(responseJson: String): Array<Movie?> {
        val response = JSONObject(responseJson)
        val movies = response.getJSONArray(RESULTS)
        val result: Array<Movie?> = arrayOfNulls<Movie?>(movies.length())
        // TODO (coyuen): switch to Java 1.8 and use more elegant stream()
        /**
         * Shorthand for loop syntax
         *
         * https://stackoverflow.com/questions/36184641/kotlin-iterate-through-a-jsonarray
         */
        for (i in 0..(movies.length() - 1)) {
            val movie = movies.getJSONObject(i)
            result[i] = parseMovieJson(movie.toString())
        }
        return result
    }

    /**
     * TODO (coyuen): Switch to using the Array<T> syntax to allow for array-level class parsing
     *
     * https://stackoverflow.com/questions/45603236/using-gson-in-kotlin-to-parse-json-array
     */
    fun parseMovieJson(movieJson: String): Movie {
        val gson = Gson()
        /**
         * GSON is a Java library, so have to pass in with ".java" extension
         * Also unclear on how to convert to array class with this necessary extension so splitting
         *
         * https://www.baeldung.com/kotlin-json-convert-data-class
         */
        return gson.fromJson(movieJson, Movie::class.java)
    }
}